package com.sunkang.common;

/**
 * @author 孙康
 * @date 2017/4/6
 * Describe：保存常量
 */
public class Constants {

    /**
     * 应用访问路劲
     */
    public final static String DOMIN_NAME="http://sunkang.wicp.net/wechat";
    /**
     * 百度地图api访问ak
     */
    public final static String BAIDU_AK="zG9ZHEURUd0WH20G6gQ8HRRa";

    /**
     * 第三方用户唯一凭证
     */
    public final static String APP_ID="wx8790dca489d4979a";

    /**
     * 第三方用户唯一凭证密钥，即appsecret
     */
    public final static String SECRET="1d3b2be33d7940d5cea04a28668f7b02";

    /**
     *获取access_token
     */
    public final static String GRANT_TYPE_CREDENTIAL="client_credential";

    /**
     * 公众平台填写的token
     */
    public final static String TOKEN="sunkang";

    /**
     * 转成xml消息时是否加上<![CDATA[  ]]> ,为true则加上，反之不加
     */
    public final static boolean IS_ADD_CDATA=true;

    /**
     * 请求消息类型：文本
     */
    public final static String REQ_MESSAGE_TYPE_TEXT="text";

    /**
     * 请求消息类型：图片
     */
    public final static String REQ_MESSAGE_TYPE_IMAGE="image";

    /**
     * 请求消息类型：语音
     */
    public final static String REQ_MESSAGE_TYPE_VOICE="voice";

    /**
     * 请求消息类型：视频
     */
    public final static String REQ_MESSAGE_TYPE_VIDEO="video";

    /**
     * 请求消息类型：小视频
     */
    public final static String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";

    /**
     * 请求消息类型：位置
     */
    public final static String REQ_MESSAGE_TYPE_LOCATION="location";

    /**
     * 请求消息类型：链接
     */
    public final static String REQ_MESSAGE_TYPE_LINK="link";

    /**
     * 请求消息类型：事件推送
     */
    public final static String REQ_MESSAGE_TYPE_EVENT="event";

    /**
     * 事件类型：订阅
     */
    public final static String EVENT_TYPE_SUBSCRIBE="subscribe";

    /**
     * 事件类型：取消订阅
     */
    public final static String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";

    /**
     * 事件类型：已关注用户扫描二维码
     */
    public final static String EVENT_TYPE_SCAN="SCAN";

    /**
     * 事件类型：上报地理位置
     */
    public final static String EVENT_TYPE_LOCATION="LOCATION";

    /**
     * 事件类型：菜单点击
     */
    public final static String EVENT_TYPE_CLICK="CLICK";

    /**
     * 事件类型：点击菜单跳转链接时的事件推送
     */
    public final static String EVENT_TYPE_VIEW="VIEW";

    /**
     * 响应消息：文本
     */
    public final static String RESP_MESSAGE_TYPE_TEXT="text";

    /**
     * 响应消息：图片
     */
    public final static String RESP_MESSAGE_TYPE_IMAGE="image";

    /**
     * 响应消息：语音
     */
    public final static String RESP_MESSAGE_TYPE_VOICE="voice";

    /**
     * 响应消息：视频
     */
    public final static String RESP_MESSAGE_TYPE_VIDEO="video";

    /**
     * 响应消息：音乐
     */
    public final static String RESP_MESSAGE_TYPE_MUSIC="music";

    /**
     * 响应消息：图文
     */
    public final static String RESP_MESSAGE_TYPE_NEWS="news";

}
