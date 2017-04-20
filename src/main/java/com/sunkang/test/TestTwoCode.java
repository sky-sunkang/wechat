package com.sunkang.test;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：测试创建二维码
 */
public class TestTwoCode {

    private static String qrcode="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    public static void main(String[] args) throws IOException {
        String accessToken="asPrAFFEhE1ZFP8C8BH5vojg5DiLXED1AL9eH7_l5cYsrvBXL5u9kbe3uy-hsmRdTpgcAd_fV02ssih8VRfLOe1vIyJZPCVz3mE15ZODqu78arhdz5Rw1rstn1S4bpH1UYPgAFAQUL";
        String path=String.format(qrcode,accessToken);
        URL url=new URL(path);
        URLConnection urlConnection=url.openConnection();
        urlConnection.setDoOutput(true);
        OutputStream os=urlConnection.getOutputStream();
        //获取临时二维码的参数post的json数据
        String postData="{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        //获取永久二维码
        postData="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        os.write(postData.getBytes("utf-8"));
        os.flush();
        InputStream is=urlConnection.getInputStream();
        System.out.println(IOUtils.toString(is,"UTF-8"));
        os.close();
        is.close();

    }
}
