package com.sunkang.service;

import com.sunkang.common.Constants;
import com.sunkang.entity.resp.ImageMessage;
import com.sunkang.entity.resp.TextMessage;
import com.sunkang.entity.resp.VideoMessage;
import com.sunkang.entity.resp.VoiceMessage;
import com.sunkang.entity.resp.base.Image;
import com.sunkang.entity.resp.base.Video;
import com.sunkang.entity.resp.base.Voice;
import com.sunkang.utils.XmlObjectUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 孙康
 * @date 2017/4/7
 * Describe：处理微信消息
 */
@Service
public class MessageService {

    private static Logger logger=Logger.getLogger(MessageService.class);

    /**
     * 处理文本消息,并且返回要回复的消息
     * @param messageMap 消息封装的map
     * @return
     */
    public String handelTextMessage(Map<String,String> messageMap){
        //将消息保存到数据库中
        String content= messageMap.get("Content");//消息内容

        //响应文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent(content);

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理图片消息
     * @param messageMap
     * @return
     */
    public String handelImageMessage(Map<String,String> messageMap){
        //将消息保存至数据库
        String picUrl=messageMap.get("PicUrl");//图片链接
        String mediaId=messageMap.get("MediaId");//语音媒体id


        //响应图片消息
        ImageMessage imageMessage=new ImageMessage();
        imageMessage.setToUserName(messageMap.get("FromUserName"));
        imageMessage.setFromUserName(messageMap.get("ToUserName"));
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_IMAGE);
        Image image=new Image();
        image.setMediaId(messageMap.get("MediaId"));
        imageMessage.setImage(image);

        //转换成xml
        Map<String,Class> alias=new HashMap<>();
        alias.put("Image",Image.class);
        alias.put("xml",ImageMessage.class);
        return XmlObjectUtils.object2Xml(imageMessage,alias);
    }

    /**
     * 处理语音消息，返回相同语音消息
     * @param messageMap
     * @return
     */
    public String handelVoiceMessage(Map<String,String> messageMap){
        //将消息保存至数据库
        String mediaId=messageMap.get("MediaId");//语音媒体id
        String format=messageMap.get("Format");//语音格式
        String recognition=messageMap.get("Recognition");//语音识别结果，需要开启语音识别

        //响应同样的语音消息
        VoiceMessage voiceMessage=new VoiceMessage();
        voiceMessage.setToUserName(messageMap.get("FromUserName"));
        voiceMessage.setFromUserName(messageMap.get("ToUserName"));
        voiceMessage.setCreateTime(new Date().getTime());
        voiceMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_VOICE);
        Voice voice=new Voice();
        voice.setMediaId(messageMap.get("MediaId"));
        voiceMessage.setVoice(voice);

        //转换成xml
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",VoiceMessage.class);
        alias.put("VOICE",Voice.class);
        return XmlObjectUtils.object2Xml(voiceMessage,alias);
    }


    /**
     * 处理视频消息，并返回视频消息
     * @param messageMap
     * @return
     */
    public String handelVideoMessage(Map<String,String> messageMap){

        String mediaId=messageMap.get("MediaId");//视频消息id
        String thumbMediaId=messageMap.get("ThumbMediaId");//语音缩略图id

        //响应视频消息
        VideoMessage videoMessage=new VideoMessage();
        videoMessage.setToUserName(messageMap.get("FromUserName"));
        videoMessage.setFromUserName(messageMap.get("ToUserName"));
        videoMessage.setCreateTime(new Date().getTime());
        Video video=new Video();
        video.setMediaId(messageMap.get("MediaId"));
        video.setTitle("我是标题");
        video.setDescription("我不晓得怎么描述");
        videoMessage.setVideo(video);
        videoMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_VIDEO);

        //转换成xml
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",VideoMessage.class);
        alias.put("Video",Video.class);
        return XmlObjectUtils.object2Xml(videoMessage,alias);
    }

    /**
     * 处理小视频消息
     * @param messageMap
     * @return
     */
    public String handelShortVideo(Map<String,String> messageMap){
        String mediaId=messageMap.get("MediaId");
        String thumbMediaId=messageMap.get("ThumbMediaId");

        //响应视频消息
        VideoMessage videoMessage=new VideoMessage();
        videoMessage.setToUserName(messageMap.get("FromUserName"));
        videoMessage.setFromUserName(messageMap.get("ToUserName"));
        videoMessage.setCreateTime(new Date().getTime());
        Video video=new Video();
        video.setMediaId(messageMap.get("MediaId"));
        video.setTitle("我是标题");
        video.setDescription("我不晓得怎么描述");
        videoMessage.setVideo(video);
        videoMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_VIDEO);

        //转换成xml
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",VideoMessage.class);
        alias.put("Video",Video.class);

        System.out.print("");
        return XmlObjectUtils.object2Xml(videoMessage,alias);
    }

    /**
     * 处理位置消息
     * @param messageMap
     * @return
     */
    public String handelLocationMessage(Map<String ,String> messageMap){
        String location_X= messageMap.get("Location_X");//纬度
        String location_Y=messageMap.get("Location_Y");//经度
        String scale= messageMap.get("Scale");//缩放比例
        String label=messageMap.get("Label");//位置信息

        //响应一个文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("你的位置在\n【纬度："+location_X+"\n经度："+location_Y+"\n缩放比例："+scale+"\n位置信息："+label+"\n】");

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理链接消息
     * @param messageMap
     * @return
     */
    public String handelLinkMessage(Map<String,String> messageMap){
        String title=messageMap.get("Title");//标题
        String description=messageMap.get("Description");//消息描述
        String url=messageMap.get("Url");//消息链接

        //响应一个文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("你链接信息\n【标题："+title+"\n描述："+description+"\n消息链接："+url+"\n】");

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理关注事件
     * @param messageMap
     * @return
     */
    public String handelSubscribeEvent(Map<String,String> messageMap){

        //响应一个文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("你关注我干什么？");

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理取消关注事件
     * @param messageMap
     * @return
     */
    public String handelUnsubscribeEvent(Map<String,String> messageMap){


        return null;
    }

    /**
     * 处理扫描二维码事件
     * @param messageMap
     * @return
     */
    public String handelScanEvent(Map<String,String> messageMap){
        String eventKey=messageMap.get("EventKey");//事件KEY值，qrscene_为前缀，后面为二维码的参数值
        String tickey=messageMap.get("TICKET");//二维码的ticket，可用来换取二维码图片

        //响应一个文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("二维码参数为：【"+eventKey+"】");

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理上报地理位置事件
     * @param messageMap
     * @return
     */
    public String handelLocationEvent(Map<String,String> messageMap){
        String latitude=messageMap.get("Latitude");//地理位置纬度
        String longitude=messageMap.get("Longitude");//地理位置经度
        String precision=messageMap.get("Precision");//地理位置精度

        //响应一个文本消息
        TextMessage textMessage=new TextMessage();
        textMessage.setToUserName(messageMap.get("FromUserName"));
        textMessage.setFromUserName(messageMap.get("ToUserName"));
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setContent("你的位置在\n【纬度："+latitude+"\n经度："+longitude+"\n精度："+precision+"\n】");

        //转换消息为需要的xml格式
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",TextMessage.class);
        return XmlObjectUtils.object2Xml(textMessage,alias);
    }

    /**
     * 处理上报地理位置事件
     * @param messageMap
     * @return
     */
    public String handelClickEvent(Map<String,String> messageMap){
        String eventKey=messageMap.get("EventKey");//事件KEY值，与自定义菜单接口中KEY值对应

        return null;
    }

    /**
     * 处理点击菜单跳转链接时的事件推送
     * @param messageMap
     * @return
     */
    public String handelViewEvent(Map<String,String> messageMap){
        String eventKey=messageMap.get("EventKey");//事件KEY值，与自定义菜单接口中KEY值对应

        return null;
    }

    /**
     * 获得素材列表
     * @param type
     * @param offset
     * @param count
     */
    public static void getMaterialList(String type,int offset,int count){
        //获取地址
        String path="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+Constants.TOKEN;
        try {
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
//            urlConnection.setRequestProperty("","");
            urlConnection.setDoOutput(true);
            OutputStream os=urlConnection.getOutputStream();
            os.write("{type:'image',offset:0,count:20}".getBytes());
            os.flush();
            InputStream is=urlConnection.getInputStream();
            String str= IOUtils.toString(is,"utf-8");
            System.out.println(str);
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取素材失败",e);
        }
    }

}
