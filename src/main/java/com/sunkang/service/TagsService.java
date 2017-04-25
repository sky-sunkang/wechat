package com.sunkang.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunkang.entity.user.Tag;
import com.sunkang.exception.WeChatException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：标签的操作
 */
@Service
public class TagsService {

    private static Logger log=Logger.getLogger(TagsService.class);

    @Autowired
    private TokenService tokenService;

    //创建标签
    private static String createTagsPath="https://api.weixin.qq.com/cgi-bin/tags/create?access_token=%s";

    //获得标签
    private static String getTags="https://api.weixin.qq.com/cgi-bin/tags/get?access_token=%s";

    //更新标签
    private static String updateTag="https://api.weixin.qq.com/cgi-bin/tags/update?access_token=%s";

    //删除标签
    private static String deleteTag="https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=%s";

    /**
     * 创建标签
     * @param name 标签名称
     * @return 标签的id
     */
    public String createTags(String name) throws WeChatException {
        OutputStream os=null;
        InputStream is=null;
        try {
            String accessToken=tokenService.getAccessToken();
            String path=String.format(createTagsPath,accessToken);
            //调用接口
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            //传入参数
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            String tagsJson="{\"tag\":{\"name\":\""+name+"\"}}";
            os.write(tagsJson.getBytes("utf-8"));
            os.flush();
            is=urlConnection.getInputStream();
            String jsonStr= IOUtils.toString(is,"utf-8");
            log.debug("创建标签结果："+jsonStr);
//            {"errcode":45158,"errmsg":"tag name too long hint: [flpwta0904vr20]"}
            //创建标签错误
            if(jsonStr.contains("\"errcode\":")){
                throw new WeChatException("创建标签错误："+jsonStr);
            }
            //解析返回的字符串
            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONObject tagObject=jsonObject.getJSONObject("tag");
            String id=tagObject.getString("id");
            return id;
        } catch (IOException e) {
            log.error("创建标签失败",e);
            throw new WeChatException("创建标签失败",e);
        }finally {
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
     * 获取标签列表
     * @return 标签集合
     * @throws WeChatException
     */
    public List<Tag> getTags() throws WeChatException {
        InputStream is=null;
        try {
            String accessToken=tokenService.getAccessToken();
            //获取标签的api路径
            String path=String.format(getTags,accessToken);
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            is=urlConnection.getInputStream();
            String jsonStr=IOUtils.toString(is,"UTF-8");
            log.debug("标签为："+jsonStr);
            //如果返回为错误，则抛出错误
            if(jsonStr.contains("{\"errcode\":")){
                throw new WeChatException("获取标签错误："+jsonStr);
            }
            //解析返回的json串

            JSONObject jsonObject=JSONObject.fromObject(jsonStr);
            JSONArray jsonArray=jsonObject.getJSONArray("tags");
            List<Tag> tags=new ArrayList<>();
            for(int i=0;i<jsonArray.size();i++){
                Tag tag=new Tag();
                JSONObject tagJson=jsonArray.getJSONObject(i);
                tag.setId(tagJson.getInt("id"));
                tag.setName(tagJson.getString("name"));
                tag.setCount(tagJson.getInt("count"));
                tags.add(tag);
            }
            return tags;
        } catch (IOException e) {
            throw new WeChatException("获取标签失败！",e);
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 组装回复的标签消息
     * @return 标签消息
     * @throws WeChatException
     */
    public String assembleTagsMessage() throws WeChatException {
        StringBuffer count=new StringBuffer("");
        List<Tag> tags=getTags();
        for (Tag tag:tags){
            count.append("标签ID:");
            count.append(tag.getId());
            count.append(",名称:");
            count.append(tag.getName());
            count.append(",客户数");
            count.append(tag.getCount());
            count.append("\n");
        }
        return count.toString();

    }

    /**
     * 更新标签
     * @param tag 要更新的标签对象
     * @throws WeChatException
     */
    public void updateTag(Tag tag) throws WeChatException {
        if(tag==null||tag.getId()==null){
            throw new WeChatException("没有选择要修改的标签");
        }
        InputStream is=null;
        OutputStream os=null;
        try {
            String accessToken=tokenService.getAccessToken();
            //修改标签的接口地址
            String path=String.format(updateTag,accessToken);
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            //post传参
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            String tagJson="{\"tag\" : {\"id\" :"+tag.getId()+", \"name\" : \""+tag.getName()+"\"}}";
            os.write(tagJson.getBytes("utf-8"));
            os.flush();
            //获取响应json
            is=urlConnection.getInputStream();
            String posJson=IOUtils.toString(is,"utf-8");
            //解析响应字符串
            JSONObject jsonObject=JSONObject.fromObject(posJson);
            Integer errCode=jsonObject.getInt("errcode");
            if(errCode!=0){
                throw new WeChatException("修改标签失败："+posJson);
            }
        } catch (IOException e) {
            throw new WeChatException("修改标签失败",e);
        }finally {
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

    public void deleteTag(int tagId) throws WeChatException {
        InputStream is=null;
        OutputStream os=null;
        try {
//            String accessToken=tokenService.getAccessToken();
            String accessToken="FdeoitP5kihQ6zL13wQLRD1FX64b44Hw3S9WLcZEw3SzdTMaiCJgD7E8DlM9n_BGgisSJpjE4woDfAtpXXmeq0GE2AhanGbJlFsm7er50WGVIvBJF8vMpvm6wSOyId4uRLWgAJAICN";
            //删除标签的接口路径
            String path=String.format(deleteTag,accessToken);
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            //post传参
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            String reqJson="{\"tag\":{\"id\":"+tagId+"}}";
            os.write(reqJson.getBytes("utf-8"));
            //获取响应
            is=urlConnection.getInputStream();
            String posJson=IOUtils.toString(is,"utf-8");
            //解析响应字符串
            JSONObject jsonObject=JSONObject.fromObject(posJson);
            Integer errCode=jsonObject.getInt("errcode");
            if(errCode!=0){
                throw new WeChatException("修改标签失败："+posJson);
            }

        } catch (IOException e) {
            throw new WeChatException("删除标签失败",e);
        }

    }
    public static void main(String[] args) throws WeChatException {
        TagsService tagsService=new TagsService();
        Tag tag=new Tag();
        tag.setId(100);
        tag.setName("粉丝");
        tagsService.deleteTag(101);
    }
}
