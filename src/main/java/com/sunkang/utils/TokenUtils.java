package com.sunkang.utils;

import com.sunkang.entity.AccessToken;
import com.sunkang.exception.TokenException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：获得token的
 */
public class TokenUtils {

    private static final Logger log=Logger.getLogger(TokenUtils.class);

    /**
     * 获取token
     * @param grantType 获取access_token填写client_credential
     * @param appId 第三方用户唯一凭证
     * @param secret 第三方用户唯一凭证密钥，即appsecret
     * @return 公众号接口调用唯一凭证
     * @throws Exception
     */
    public static AccessToken getAccessToken(String grantType,String appId,String secret) throws Exception {
        //获取token所要调用的地址
        String path="https://api.weixin.qq.com/cgi-bin/token?"+
                "grant_type="+grantType+"&appid="+appId+
                "&secret="+secret;
        URL url=new URL(path);
        URLConnection connection=url.openConnection();
        //获得输出流
        InputStream is= connection.getInputStream();
        String isStr= IOUtils.toString(is,"utf-8");
        log.debug("access_token="+isStr);

        is.close();
        //解析所获得的json字符串
        JSONObject jsonObject=JSONObject.fromObject(isStr);

        //如果appid无效等将返回该json  {"errcode":40013,"errmsg":"invalid appid"}
        int errcode=jsonObject.getInt("errcode");
        if(errcode!=0){
            throw new TokenException("errcode="+errcode+"  errmsg=" +jsonObject.getInt("errmsg"));
        }


        AccessToken accessToken=new AccessToken();
        accessToken.setAccessToken(jsonObject.getString("access_token"));
        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));


        return accessToken;
    }
}
