package com.sunkang.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sunkang.entity.custom.*;
import com.sunkang.entity.custom.base.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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

    /**
     *  组装语音消息
     * @param openId 消息发送对象
     * @param mediaId 消息媒体id
     * @param kfAccount 客服账户
     * @return
     * @throws JsonProcessingException
     */
    public static String makeVoiceCustomMessage(String openId,String mediaId,String kfAccount) throws JsonProcessingException {
        VoiceCustomMessage voiceCustomMessage=new VoiceCustomMessage();
        voiceCustomMessage.setTouser(openId);
        voiceCustomMessage.setMsgtype("voice");
        Voice voice=new Voice();
        voice.setMedia_id(mediaId);
        voiceCustomMessage.setVoice(voice);
        //如果有客户账号
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            voiceCustomMessage.setCustomservice(customservice);
        }
        return JsonUtils.object2Json(voiceCustomMessage, JsonInclude.Include.NON_NULL);
    }

    /**
     * 组装视频消息
     * @param openId 消息接受对象
     * @param video 视频对象
     * @param kfAccount 客服账号
     * @return
     * @throws JsonProcessingException
     */
    public static String makeVideoCustomMessage(String openId,Video video,String kfAccount) throws JsonProcessingException {
        VideoCustomMessage videoCustomMessage=new VideoCustomMessage();
        videoCustomMessage.setTouser(openId);
        videoCustomMessage.setMsgtype("video");
        videoCustomMessage.setVideo(video);
        //如果有客户账号
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            videoCustomMessage.setCustomservice(customservice);
        }
        return JsonUtils.object2Json(videoCustomMessage, JsonInclude.Include.NON_NULL);

    }

    /**
     * 组装音乐消息
     * @param openId 消息接受对象
     * @param music 音乐对象
     * @param kfAccount 客服账号
     * @return
     * @throws JsonProcessingException
     */
    public static String makeMusicCustomMessage(String openId,Music music,String kfAccount) throws JsonProcessingException {
        MusicCustomMessage musicCustomMessage=new MusicCustomMessage();
        musicCustomMessage.setTouser(openId);
        musicCustomMessage.setMsgtype("music");
        musicCustomMessage.setMusic(music);
        //如果有客户账号
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            musicCustomMessage.setCustomservice(customservice);
        }
        return JsonUtils.object2Json(musicCustomMessage, JsonInclude.Include.NON_NULL);

    }


    /**
     * 组装图文消息
     * @param openId 消息发送对象
     * @param articles 图文消息列表
     * @param kfAccount 客服账户
     * @return
     */
    public static String makeNewsCustomMessage(String openId, Articles articles, String kfAccount) throws JsonProcessingException {
        NewsCustomMessage newsCustomMessage=new NewsCustomMessage();
        newsCustomMessage.setNews(articles);
        newsCustomMessage.setTouser(openId);
        newsCustomMessage.setMsgtype("news");

        //如果有客户账号
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            newsCustomMessage.setCustomservice(customservice);
        }
        return JsonUtils.object2Json(newsCustomMessage, JsonInclude.Include.NON_NULL);
    }

    /**
     * 组装微信卡券消息
     * @param openId 消息接收对象
     * @param cardId 卡券id
     * @param kfAccount 客服账号
     * @return
     * @throws JsonProcessingException
     */
    public static String makeWxcardCustomMessage(String openId, String cardId, String kfAccount) throws JsonProcessingException {
        WxcardCustomMessage wxcardCustomMessage=new WxcardCustomMessage();
        wxcardCustomMessage.setTouser(openId);
        wxcardCustomMessage.setMsgtype("wxcard");
        Wxcard wxcard=new Wxcard();
        wxcard.setCard_id(cardId);
        wxcardCustomMessage.setWxcard(wxcard);
        //如果有客户账号
        if(kfAccount!=null&&kfAccount.length()>0){
            Customservice customservice=new Customservice();
            customservice.setKf_account(kfAccount);
            wxcardCustomMessage.setCustomservice(customservice);
        }
        return JsonUtils.object2Json(wxcardCustomMessage, JsonInclude.Include.NON_NULL);
    }


    public static void main(String[] args) throws IOException {

        List<Article> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            Article articles=new Article();
            articles.setTitle("开源中国");
            articles.setDescription("我就是开源中国");
            articles.setPicUrl(null);
            articles.setUrl("http://www.oschina.net/");
            list.add(articles);
        }

        Articles articles=new Articles();
        articles.setArticles(list);
        String jsonStr=makeNewsCustomMessage("oIWvIv3VVODA3oCAAFqbFs0UBDkg",articles,null);
        System.out.println(jsonStr);

        //发送消息
        String path="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ICTkb1p0siew8i292iUVimzE4Zs2MoGKdTAIB8GPSqZFHqrme7GrdGEisanDtH8AsOQ0b9j5JouAI_8ndcWTCZR4TywdvDOi0hhh5k5PrbIng4lk_8G1BMMtI_Xe4M9CHARaAGAVOF";

        URL url=new URL(path);
        URLConnection connection=url.openConnection();
        connection.setDoOutput(true);
        OutputStream os=connection.getOutputStream();
        os.write(jsonStr.getBytes("utf-8"));
        InputStream is=connection.getInputStream();
        //{"errcode":0,"errmsg":"ok"}
        System.out.println(IOUtils.toString(is));
        os.flush();
        os.close();
        is.close();
    }
}
