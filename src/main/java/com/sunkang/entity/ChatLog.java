package com.sunkang.entity;

/**
 * @author 孙康
 * @date 2017/5/4
 * Describe：聊天记录表
 */
public class ChatLog {

    //主键
    private Integer id;

    //用户openid
    private String openId;

    //消息创建时间
    private String createTime;

    //用户发送消息
    private String reqMsg;

    //公众号回复消息
    private String respMsg;

    //聊天类型
    private Integer chatCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReqMsg() {
        return reqMsg;
    }

    public void setReqMsg(String reqMsg) {
        this.reqMsg = reqMsg;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Integer getChatCategory() {
        return chatCategory;
    }

    public void setChatCategory(Integer chatCategory) {
        this.chatCategory = chatCategory;
    }
}
