package com.sunkang.controller;

import com.sunkang.common.Constants;
import com.sunkang.entity.resp.TextMessage;
import com.sunkang.service.MessageService;
import com.sunkang.utils.BytesStringUtils;
import com.sunkang.utils.MessageUtils;
import com.sunkang.utils.XmlObjectUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by sunkang on 2017/4/5.
 * 微信服务控制器
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger=Logger.getLogger(WechatController.class);

    @Autowired
    private MessageService messageService;

    /**
     * token验证，get请求是token验证
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public  String tokenCheck(String  signature,String timestamp,String nonce,String echostr){
        logger.debug("开始验证token【"+signature+","+timestamp+","+nonce+","+echostr+"】...");
        try {
            //1）将token(公众平台填的token)、timestamp、nonce三个参数进行字典序排序
            String[] arr=new String[]{Constants.TOKEN,timestamp,nonce};
            Arrays.sort(arr);

            //2）将三个参数字符串拼接成一个字符串进行sha1加密
            String content=arr[0].concat(arr[1]).concat(arr[2]);

            MessageDigest md=MessageDigest.getInstance("SHA-1");
            byte[] digest=md.digest(content.getBytes());
            //将byte数据转成字符串
            String ciphertext= BytesStringUtils.bytesToStr(digest);

            //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
            if(ciphertext!=null &&ciphertext.equals(signature.toUpperCase())){
                //验证成功，返回echostr
                logger.debug("token验证通过...");
                return echostr;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("token验证失败",e);
        }
        logger.error("token验证失败");
        return null;
    }

    /**
     * 微信消息的处理，post请求
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String dispose(HttpServletRequest request, HttpServletResponse response){
        logger.debug("微信消息处理开始...");
        InputStream is=null;
        //获得请求的流
        try {
            is=request.getInputStream();
            Map<String,String> messageMap= MessageUtils.parseXml(is);
            logger.debug("收到的消息为【 "+messageMap.toString()+" 】");
            //回复的消息
            String respMessage=null;

            //消息类型
            String msgType=messageMap.get("MsgType");
            switch (msgType){
                case Constants.REQ_MESSAGE_TYPE_TEXT ://文本消息
                    respMessage=messageService.handelTextMessage(messageMap);
                    break;
                case Constants.REQ_MESSAGE_TYPE_IMAGE://图片消息
                    respMessage=messageService.handelImageMessage(messageMap);
                    break;
                case "voice"://语音消息
                    String mediaId=messageMap.get("MediaId");//语音媒体id
                    String format=messageMap.get("Format");//语音格式
                    String recognition=messageMap.get("Recognition");//语音识别结果，需要开启语音识别
                    break;
                case "video"://视频消息
                    mediaId=messageMap.get("MediaId");//视频消息id
                    String thumbMediaId=messageMap.get("ThumbMediaId");//语音缩略图id
                    break;
                case "shortvideo"://小视频
                    mediaId=messageMap.get("MediaId");
                    thumbMediaId=messageMap.get("ThumbMediaId");
                    break;
                case "location"://位置消息
                    String location_X= messageMap.get("Location_X");//纬度
                    String location_Y=messageMap.get("Location_Y");//经度
                    String scale= messageMap.get("Scale");//缩放比例
                    String label=messageMap.get("Label");//位置信息
                    break;
                case "link"://链接消息
                    String title=messageMap.get("Title");//标题
                    String description=messageMap.get("Description");//消息描述
                    String url=messageMap.get("Url");//消息链接
                    break;
                default:
                    logger.error("未知消息");
            }

            //回复消息给用户
            if(respMessage!=null&&!respMessage.isEmpty()){
                return respMessage;
            }
        } catch (Exception e) {
            logger.error("消息处理失败！",e);
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
