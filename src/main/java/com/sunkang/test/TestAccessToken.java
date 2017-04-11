package com.sunkang.test;

import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：测试获取access_token
 */
public class TestAccessToken {

    public static void main(String[] args) throws Exception {
        String grant_type="client_credential";
        String appid="wx8790dca489d4979a";
        String secret="1d3b2be33d7940d5cea04a28668f7b02";
        String path="https://api.weixin.qq.com/cgi-bin/token?"+
                "grant_type="+grant_type+"&appid="+appid+
                "&secret="+secret;
        URL url=new URL(path);
        URLConnection connection=url.openConnection();

        InputStream is= connection.getInputStream();
        //将输出流转换成字符串
        String isStr= IOUtils.toString(is,"utf-8");
        is.close();
        System.out.println(isStr);

        JSONObject jsonObject=JSONObject.fromObject(isStr);
        String accessToken=jsonObject.getString("access_token");
        int expiresIn=jsonObject.getInt("expires_in");

        System.out.println(accessToken+"  "+expiresIn);

    }
}
