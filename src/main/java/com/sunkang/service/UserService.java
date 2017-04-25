package com.sunkang.service;

import com.sunkang.entity.user.FollowUserList;
import com.sunkang.entity.user.UserInfo;
import com.sunkang.exception.WeChatException;
import com.sunkang.utils.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：用户服务
 */
@Service
public class UserService {
    private static Logger log=Logger.getLogger(UserService.class);

    /**
     * 获取用户信息
     */
    private static String userInfo="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN ";

    /**
     * 批量获取用户信息
     */
    private static String userInfoBatch="https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=%s";

    /**
     * 获取关注这列表
     */
    private static String followUserInfo="https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

    @Autowired
    private TokenService tokenService;

    /**
     * 根据用户openid获取用户信息
     * @param openId 用户的openid
     * @return 用户信息
     * @throws WeChatException
     */
    public UserInfo getUserByOpenid(String openId) throws WeChatException {
        InputStream is=null;
        try {
            String accessToken=tokenService.getAccessToken();
            //获取用户信息的接口路径
            String path=String.format(userInfo,accessToken,openId);
            //链接获取响应
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            is=urlConnection.getInputStream();
            String userJson= IOUtils.toString(is,"utf-8");
            log.debug("用户信息为："+userJson);

            //如果包含error则说明调用接口报错
            if(userJson.contains("\"errcode\":")){
                log.error("批量接口调用出错："+userJson);
                throw new WeChatException("批量用户信息拉取接口调用出错："+userJson);
            }

            //解析响应的json字符串，并转入对象中
            JSONObject jsonObject=JSONObject.fromObject(userJson);
            UserInfo userInfo=new UserInfo();
            int subscribe= jsonObject.getInt("subscribe") ;
            //未关注，拉取不到其他信息
            if(subscribe==0){
                userInfo.setSubscribe(jsonObject.getInt("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
                userInfo.setOpenId(jsonObject.getString("openid"));//用户的标识，对当前公众号唯一
            }else{
                userInfo.setSubscribe(jsonObject.getInt("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
                userInfo.setOpenId(jsonObject.getString("openid"));//用户的标识，对当前公众号唯一
                userInfo.setNickName(jsonObject.getString("nickname"));//用户的昵称
                userInfo.setSex(jsonObject.getString("sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
                userInfo.setCity(jsonObject.getString("city"));//用户所在城市
                userInfo.setCountry(jsonObject.getString("country"));//用户所在国家
                userInfo.setProvince(jsonObject.getString("province"));//用户所在省份
                userInfo.setLanguage(jsonObject.getString("language"));//用户的语言，简体中文为zh_CN
                userInfo.setHeadImageUrl(jsonObject.getString("headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
                userInfo.setSubscribe(jsonObject.getInt("subscribe_time"));//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
                userInfo.setRemark(jsonObject.getString("remark"));//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
                userInfo.setGroupid(jsonObject.getString("groupid"));//用户所在的分组ID（兼容旧的用户分组接口）
                userInfo.setTagid_list(jsonObject.getString("tagid_list"));//用户被打上的标签ID列表
            }
            return userInfo;

        } catch (IOException e) {
            throw new WeChatException("获取用户信息失败！",e);
        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据用户openid批量获取用户的信息
     * @param openids 多个用户openid
     * @param lang 语言
     * @return 多个用户信息
     * @throws WeChatException
     */
    public List<UserInfo> getUserList(String[] openids,String lang) throws WeChatException {
        InputStream is=null;
        OutputStream os=null;
        try {
            String accessToken=tokenService.getAccessToken();
            //批量获取用户的地址
            String path=String.format(userInfoBatch,accessToken);
            //链接获取响应
            URL url = new URL(path);
            URLConnection urlConnection=url.openConnection();
            //post方式传入参数
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            //拼接传入参数字符串
            StringBuffer sb= new StringBuffer("{ \"user_list\": [");
            for(String openid:openids){
                sb.append("{\"openid\": \"");
                sb.append(openid);
                sb.append("\",\"lang\": \"");
                sb.append(lang);
                sb.append("\"},");
            }
            //删除左后一个，
            sb.deleteCharAt(sb.length());
            sb.append("]}");
            os.write(sb.toString().getBytes("utf-8"));
            os.flush();

            is=urlConnection.getInputStream();
            String userJson= IOUtils.toString(is,"utf-8");
            log.debug("用户信息为："+userJson);
            //如果包含error则说明调用接口报错
            if(userJson.contains("\"errcode\":")){
                log.error("批量接口调用出错："+userJson);
                throw new WeChatException("批量用户信息拉取接口调用出错："+userJson);
            }

            //解析json
            JSONObject jsonObject=JSONObject.fromObject(userJson);
            JSONArray jsonArray=jsonObject.getJSONArray("user_info_list");
            List<UserInfo> userInfos=new ArrayList<>();
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                UserInfo userInfo=new UserInfo();
                int subscribe= jsonObject.getInt("subscribe") ;
                //未关注，拉取不到其他信息
                if(subscribe==0){
                    userInfo.setSubscribe(jsonObject.getInt("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
                    userInfo.setOpenId(jsonObject.getString("openid"));//用户的标识，对当前公众号唯一
                    userInfos.add(userInfo);
                }else{
                    //已关注
                    userInfo.setSubscribe(jsonObject.getInt("subscribe"));//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
                    userInfo.setOpenId(jsonObject.getString("openid"));//用户的标识，对当前公众号唯一
                    userInfo.setNickName(jsonObject.getString("nickname"));//用户的昵称
                    userInfo.setSex(jsonObject.getString("sex"));//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
                    userInfo.setCity(jsonObject.getString("city"));//用户所在城市
                    userInfo.setCountry(jsonObject.getString("country"));//用户所在国家
                    userInfo.setProvince(jsonObject.getString("province"));//用户所在省份
                    userInfo.setLanguage(jsonObject.getString("language"));//用户的语言，简体中文为zh_CN
                    userInfo.setHeadImageUrl(jsonObject.getString("headimgurl"));//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
                    userInfo.setSubscribe(jsonObject.getInt("subscribe_time"));//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
                    userInfo.setRemark(jsonObject.getString("remark"));//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
                    userInfo.setGroupid(jsonObject.getString("groupid"));//用户所在的分组ID（兼容旧的用户分组接口）
                    userInfo.setTagid_list(jsonObject.getString("tagid_list"));//用户被打上的标签ID列表
                    userInfos.add(userInfo);
                }
            }
            return userInfos;
        } catch (IOException e) {
            throw new WeChatException("列表获取用户失败",e);
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


    /**
     * 获取关注用户列表
     * @param nextOpenid 从那个用户开始拉取，为空的话从第一个开始
     * @return 关注用户列表
     * @throws WeChatException
     */
    public FollowUserList getFollowUserList(String nextOpenid) throws WeChatException {
        InputStream is=null;
        try {
            //获取accesstoken
            String accessToken=tokenService.getAccessToken();
            String path;
            //如果传入了nextOpenId则带上该参数
            if(nextOpenid!=null&&nextOpenid.length()>=0){
                path=String.format(followUserInfo,accessToken)+"&next_openid="+nextOpenid;
            }else{
                path=String.format(followUserInfo,accessToken);
            }
            //链接该地址，获取返回值
            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            is=urlConnection.getInputStream();
            String usersJson=IOUtils.toString(is,"utf-8");
            log.debug("关注者列表为："+usersJson);
            //解析json字符串
            if(usersJson.contains("\"errcode\":")){
                //调用接口错误，将错误抛出
                throw new WeChatException("获取关注着列表异常："+usersJson);
            }
            JSONObject jsonObject=JSONObject.fromObject(usersJson);
            FollowUserList followUserList=new FollowUserList();
            followUserList.setTotal(jsonObject.getInt("total"));//关注者总数
            followUserList.setCount(jsonObject.getInt("count"));//拉取的用户个数
            followUserList.setNextOpenId(jsonObject.getString("next_openid"));//拉取列表的最后一个用户的OPENID
            //用户数据
            List<String> openIds=new ArrayList<>();
            JSONObject dataObject=jsonObject.getJSONObject("data");
            //用户id数组
            JSONArray jsonArray=dataObject.getJSONArray("openid");
            for(int i=0;i<jsonArray.size();i++){
                openIds.add(jsonArray.getString(i));
            }
            followUserList.setOpenIds(openIds);
            return followUserList;

        } catch (IOException e) {
            log.error("获取关注着列表异常",e);
            throw new WeChatException("获取关注着列表异常",e);
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 拼接回复查询我的信息
     * @param openId
     * @return
     * @throws WeChatException
     */
    public String assembleUserMessage(String openId) throws WeChatException {
        String content="";
        UserInfo userInfo=getUserByOpenid(openId);
        if(userInfo!=null&userInfo.getSubscribe()!=1){
            //拼装字符串
            content="标识:%s\n昵称:%s\n性别:%s\n城市:%s\n国家:%s\n省份:%s\n语言:%s\n关注时间:%s\n备注:%s\n分组ID:%s";
            content=String.format(content,userInfo.getOpenId(),userInfo.getNickName(),userInfo.getSex()=="1"?"男":"女" ,
                    userInfo.getCity(),userInfo.getCountry(),userInfo.getProvince(),userInfo.getLanguage(),
                    DateUtils.time2String(userInfo.getSubscribeTime(),"yyyy-MM-dd HH:mm:ss"),userInfo.getRemark(),userInfo.getGroupid());
        }else{
            content="你还未关注我，无法拉取你的信息，请关注后重试！";
        }
        return content;
    }
    public static void main(String[] args) throws WeChatException {
        UserService userService=new UserService();
        FollowUserList followUserList=userService.getFollowUserList(null);
        log.debug(followUserList.toString());
    }
}
