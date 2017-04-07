package com.sunkang.service;

import com.sunkang.common.Constants;
import com.sunkang.entity.resp.ImageMessage;
import com.sunkang.entity.resp.TextMessage;
import com.sunkang.utils.XmlObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
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
        imageMessage.setMediaId(messageMap.get("MediaId"));
        Map<String,Class> alias=new HashMap<>();
        alias.put("xml",ImageMessage.class);
        return XmlObjectUtils.object2Xml(imageMessage,alias);
    }
}
