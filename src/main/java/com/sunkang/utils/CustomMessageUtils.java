package com.sunkang.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunkang.entity.customMsg.ImageCustomMessage;
import com.sunkang.entity.customMsg.TextCustomMessage;
import com.sunkang.entity.customMsg.base.Customservice;
import com.sunkang.entity.customMsg.base.Image;
import com.sunkang.entity.customMsg.base.Text;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客服消息的工具类
 */
public class CustomMessageUtils {

    /**
     * 组装文本客服消息
     * @param openId 消息发送对象
     * @param content 消息内容
     * @return
     */
    public static String makeTextCustomMessage(String openId,String content){
        //对消息中的双引号进行转移
        content=content.replace("\"","\\\"");
//        {
//            "touser":"OPENID",
//                "msgtype":"text",
//                "text":
//            {
//                "content":"Hello World"
//            } ,"customservice":
//            {
//                "kf_account": "test1@kftest"
//            }
//        }
        String jsonMsg="{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";

        return String.format(jsonMsg,openId,content);
    }


    /**
     * 组装图片文本消息
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @param kfAccount 客服账户
     * @return
     */
    public static String makeImageCustomMessage(String openId,String mediaId,String kfAccount) throws JsonProcessingException {
        ImageCustomMessage imageCustomMessage=new ImageCustomMessage();
        imageCustomMessage.setMsgtype(openId);
        imageCustomMessage.setMsgtype("image");
        Image image=new Image();
        image.setMedia_id(mediaId);
        imageCustomMessage.setImage(image);
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            imageCustomMessage.setCustomservice(customservice);
        }
        String jsonMsg= JsonUtils.object2Json(imageCustomMessage, JsonInclude.Include.NON_NULL);
        return jsonMsg;
    }


    public static void main(String[] args) throws JsonProcessingException {
        Text content=new Text();
        content.setContent("呵呵呵");
        TextCustomMessage textCustomMessage=new TextCustomMessage();
        textCustomMessage.setTouser("孙康");
        textCustomMessage.setMsgtype("text");
        textCustomMessage.setContent(content);

        Customservice customservice=new Customservice();
        customservice.setKf_account("heheh");
//        textCustomMessage.setCustomservice(customservice);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println(objectMapper.writeValueAsString(textCustomMessage));

//        System.out.println(makeTextCustomMessage("ha\"hah","呵呵呵"));
    }
}
