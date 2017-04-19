package com.sunkang.service;

import com.sunkang.common.Constants;
import com.sunkang.entity.OAuthAccessToken;
import com.sunkang.entity.UserInfo;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/19
 * Describe：OAuth网页验证的服务
 */
@Service
public class OAuthService {

    private static Logger log=Logger.getLogger(OAuthService.class);

    /**
     * oauth2网页授权：获取access_token
     */
    public static String OAUTH2_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * oauth2网页授权：获取用户信息
     */
    public static String OAUTH2_USERINFO="https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";

    /**
     * 通过code获得access_token
     * @param code 用户点击确认获得的code
     * @return
     * @throws IOException
     */
    public OAuthAccessToken getAccessTokenByCode(String code) throws IOException {
        //通过一下链接获取access_token
        String path= String.format(OAUTH2_TOKEN,Constants.APP_ID,Constants.SECRET,code) ;
        URL url=new URL(path);
        URLConnection urlConnection=url.openConnection();
        InputStream is=urlConnection.getInputStream();
        //解析返回的access_token字符串
        String jsonStr=IOUtils.toString(is,"utf-8");
        log.debug("网页授权access_token="+jsonStr);
        is.close();

        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        OAuthAccessToken oAuthAccessToken=new OAuthAccessToken();
        oAuthAccessToken.setAccess_token(jsonObject.getString("access_token"));
        oAuthAccessToken.setExpires_in(jsonObject.getInt("expires_in"));
        oAuthAccessToken.setOpenid(jsonObject.getString("openid"));
        oAuthAccessToken.setRefresh_token(jsonObject.getString("refresh_token"));
        oAuthAccessToken.setScope(jsonObject.getString("scope"));
        //	只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
//        oAuthAccessToken.setUnionid(jsonObject.getString("unionid"));

        return oAuthAccessToken;

    }

    /**
     * 获取用户信息
     * @param accessToken 网页授权的accesstoken
     * @param openId 用户唯一标识
     * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    public UserInfo getUserInfo(String accessToken,String openId,String lang) throws IOException {
        //获取用户信息的api地址
        String path=String.format(OAUTH2_USERINFO,accessToken,openId,lang);
        URL url=new URL(path);
        URLConnection urlConnection=url.openConnection();
        InputStream is=urlConnection.getInputStream();
        String jsonStr=IOUtils.toString(is,"utf-8");
        log.debug("用户信息："+jsonStr);
        is.close();
        //解析json字符串
        return josn2UserInfo(jsonStr);
    }

    /**
     * 将json字符串解析成userinfo对象
     * @param jsonStr
     * @return
     */
    public UserInfo josn2UserInfo(String jsonStr){
        UserInfo userInfo=new UserInfo();
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        //用户的唯一标识
        userInfo.setOpenId(jsonObject.getString("openid"));
        //用户昵称
        userInfo.setNickName(jsonObject.getString("nickname"));
        //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        userInfo.setSex(jsonObject.getString("sex"));
        //用户个人资料填写的省份
        userInfo.setProvince(jsonObject.getString("province"));
        //普通用户个人资料填写的城市
        userInfo.setCity(jsonObject.getString("city"));
        //国家，如中国为CN
        userInfo.setCountry(jsonObject.getString("country"));
        //用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        userInfo.setHeadImageUrl(jsonObject.getString("headimgurl"));
        //用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
        userInfo.setPrivilege(jsonObject.getString("privilege"));
        return userInfo;
    }
}
