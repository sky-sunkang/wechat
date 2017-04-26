package com.sunkang.controller;

import com.sunkang.common.RedisKeyConstants;
import com.sunkang.dao.RedisRepository;
import com.sunkang.entity.Media;
import com.sunkang.exception.WeChatException;
import com.sunkang.service.TokenService;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/24
 * Describe：文件上传
 */
@Controller
public class UploadController
{

    private static Logger log=Logger.getLogger(UploadController.class);

    private static String updatePath="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

    private static String downPath="https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisRepository redisRepository;
    /**
     * 跳转到文件上传下载页面
     * @return
     */
    @RequestMapping("toUploadDown")
    public ModelAndView toUploadDown(){
        String accessToken=tokenService.getAccessToken();
        return new ModelAndView("file/upload").addObject("accessToken",accessToken);
    }

    /**
     * 文件上传（模仿一个通过谷歌浏览器控制台的文件表单提交）
     * 支持文件格式和大小
     *  文件类型     文件大小限制      格式要求
     *  image       128k            jpg
     *  voice       256k & 60s      amr | mp3
     *  video       1MB             mp4
     *  thumb       64k             jpg
     * @param req
     * @param pos
     * @param media 素材流
     * @param type 素材类型
     * @param name 素材名称
     */
    @RequestMapping(value = "fileUpload",method = RequestMethod.POST)
    public void fileUpload(HttpServletRequest req, HttpServletResponse pos, MultipartFile media,String type,String name) throws WeChatException {
        String accessToken=tokenService.getAccessToken();

        //文件不为空
        if(!media.isEmpty()){
            //将文件上传至微信服务器
            String fileName=media.getOriginalFilename();
            log.debug(fileName);
                InputStream is=null;
                OutputStream os=null;
                InputStream is1=null;
                try {
                    String path=null;
                    switch (type){
                        case "image":
                            path=String.format(updatePath,accessToken,"image");
                            break;
                        case "voice":
                            path=String.format(updatePath,accessToken,"voice");
                            break;
                        case "video":
                            path=String.format(updatePath,accessToken,"video");
                            break;
                        case "thumb":
                            path=String.format(updatePath,accessToken,"thumb");
                            break;
                        default:
                            log.error("位置文件类型："+type);
                    }
                    is=media.getInputStream();
                    URL url =new URL(path);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    // 设置请求头信息
                    urlConnection.setRequestProperty("Connection", "Keep-Alive");
                    urlConnection.setRequestProperty("Charset", "UTF-8");
                    // 设置边界
                    String boundary = "----------" + System.currentTimeMillis();
                    urlConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);

                    // 获得输出流
                    os = new DataOutputStream(urlConnection.getOutputStream());

                    // 请求正文信息
                    // 第一部分：输出表头
                    os.write(("--"+boundary+"\r\n").getBytes("utf-8"));
                    os.write(("Content-Disposition: form-data; name=\"media\"; filename=\""+fileName+"\"\r\n").getBytes("utf-8"));
                    os.write(("Content-Type:"+"application/octet-stream"+"\r\n\r\n").getBytes("utf-8"));
                    byte[] bytes=new byte[is.available()];
                    int size=0;
                    while((size=is.read(bytes))!=-1){
                        log.debug("size="+size);
                        os.write(bytes,0,size);
                    }

                    // 结尾部分
                    os.write(("\r\n--" + boundary + "--\r\n").getBytes("utf-8"));
                    os.flush();

                    //获得微信服务器响应并解析
                    is1=urlConnection.getInputStream();
                    String posJson= IOUtils.toString(is1,"utf-8");
                    if(posJson.contains("\"errcode\":")){
                        throw new WeChatException("上传文件异常"+posJson);
                    }
                    log.debug("上传文件结果："+posJson);
                    JSONObject jsonObject=JSONObject.fromObject(posJson);
                    Media media1=new Media();
                    media1.setName(name);//素材名称
                    media1.setFileName(fileName);//文件名称
                    media1.setCreateAt(jsonObject.getInt("created_at"));//创建时间
                    media1.setType(type);//媒体类型
                    if("thumb".equals(type)){
                        media1.setMediaId(jsonObject.getString("thumb_media_id"));//缩略图媒体id
                    }else {
                        media1.setMediaId(jsonObject.getString("media_id"));//媒体id
                    }
                    //保存只redis
                    redisRepository.saveObject(RedisKeyConstants.MEDIA_KEY+name,media1);
                } catch (IOException e) {
                    throw new WeChatException("上传文件异常",e);
                }finally {
                    if(is!=null){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(is1!=null){
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(os!=null){
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        }

//        log.debug("文件名称为："+file.);
    }

    /**
     * 微信服务器文件下载，只能下载图片，语音，缩略图，视频不能下载
     * @param req
     * @param pos
     * @param downFileName 要下载的素材名
     * @throws WeChatException
     */
    @RequestMapping("fileDown")
    public void fileDown(HttpServletRequest req,HttpServletResponse pos,String downFileName) throws WeChatException {
        OutputStream os=null;
        InputStream is=null;
        BufferedInputStream bis=null;
        try {
            Media media= (Media) redisRepository.getObject(RedisKeyConstants.MEDIA_KEY+ downFileName);
            String accessToken=tokenService.getAccessToken();
            //文件下载路径
            String path=String.format(downPath,accessToken,media.getMediaId());
            //从微信服务器下载文件
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            is=urlConnection.getInputStream();
            //将微信服务器的流返回给浏览器
            //设置响应头
            pos.setCharacterEncoding("utf-8");
            pos.setContentType("multipart/form-data");
            String fileName=media.getFileName();
            //文件后缀
            String type=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
            pos.setHeader("Content-Disposition","attachment;fileName=" + downFileName+"."+type);
            os=pos.getOutputStream();
            byte[] bytes=new byte[1024];
            int size=0;
            while ((size=is.read(bytes))!=-1){
                os.write(bytes,0,size);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new WeChatException("下载文件异常",e);
        }finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 测试jquery上传文件插件，
     * @param req
     * @param pos
     * @param media
     * @param fileName
     */
    @RequestMapping(value = "testAjaxFileUpload",method = RequestMethod.POST)
    public void testAjaxFileUpload(HttpServletRequest req, HttpServletResponse pos, MultipartFile media,String fileName){

        try {
            log.debug("文件名为："+fileName);
            log.debug("文件为："+media.getOriginalFilename());
            InputStream is=media.getInputStream();
            log.debug("文件大小为"+is.available());
            is.close();
            //这个js需要这样返回，否则返回的是一个html（加@ResponseBody都没用）
            pos.getWriter().print("{success:'success',msg:'错了'}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
