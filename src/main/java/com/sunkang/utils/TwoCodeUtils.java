package com.sunkang.utils;

import com.sunkang.exception.WeChatException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：获取二维码工具类
 */
public class TwoCodeUtils {

    public static Logger log=Logger.getLogger(TwoCodeUtils.class);

    /**
     * 获取Ticket的路径
     */
    private static String qrcode="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";


    /**
     * 创建二维码ticket
     * @param accessToken 调用接口的access_token
     * @param scene_id 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param isTemp true获取临时二维码，false获取永久二维码
     * @return Ticket字符串
     * @throws WeChatException io异常
     */
    public static String getTicket(String accessToken,String scene_id,Boolean isTemp) throws WeChatException {
        String path=String.format(qrcode,accessToken);
        OutputStream os=null;
        InputStream is=null;
        try {
            URL url = new URL(path);
            URLConnection urlConnection=url.openConnection();
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            String postData=null;
            if(isTemp){
                //获取临时二维码的参数post的json数据
                postData="{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
            }else{
                //获取永久二维码
                postData="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
            }
            os.write(postData.getBytes("utf-8"));
            os.flush();
            is=urlConnection.getInputStream();
            String ticketJson=IOUtils.toString(is,"UTF-8");
            log.debug("二维码的TICKET为："+ticketJson);
            //解析响应的json字符串
            JSONObject jsonObject=JSONObject.fromObject(ticketJson);
            String ticket=jsonObject.getString("ticket");
            return ticket;

        } catch (IOException e) {
            e.printStackTrace();
            throw new WeChatException("获取二维码ticket异常",e);
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
}
