package com.sunkang.job;

import com.sunkang.common.Constants;
import com.sunkang.dao.AccessTokenRepository;
import com.sunkang.entity.AccessToken;
import com.sunkang.exception.TokenException;
import com.sunkang.service.TokenService;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/13
 * Describe：定时调度获取token，并且存入redis中
 */
@Component
public class AccessTokenJob {

    private static final Logger log=Logger.getLogger(AccessTokenJob.class);

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    /**
     * 定时获取token,启动后1秒开始获取，之后每隔2小时开始获取，并存入redis中
     * @return 公众号接口调用唯一凭证
     * @throws Exception
     */
    @Scheduled(fixedRate=2*60*60*1000)
    public AccessToken getAccessTokenToRedis() throws Exception {
        //获取token所要调用的地址
        String path="https://api.weixin.qq.com/cgi-bin/token?"+
                "grant_type="+ Constants.GRANT_TYPE_CREDENTIAL+"&appid="+Constants.APP_ID+
                "&secret="+Constants.SECRET;
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
        if(is!=null&& isStr.contains("\"errcode\":")){
            throw new TokenException("errcode="+jsonObject.getInt("errcode")+"  errmsg=" +jsonObject.getInt("errmsg"));
        }


        AccessToken accessToken=new AccessToken();
        accessToken.setAccessToken(jsonObject.getString("access_token"));

        //将token存在redis中        accessToken.setExpiresIn(jsonObject.getInt("expires_in"));

        accessTokenRepository.saveAccessToken(accessToken);
        return accessToken;
    }
}
