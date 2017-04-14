package com.sunkang.entity.customMsg.base;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客户消息的基本类
 */
public class BaseCustomMessage {
    /**
     * 消息发送对象
     */
    private String touser;

    /**
     * 客服账号：如果需要以某个客服帐号来发消息则需要
     */
    private Customservice customservice;


    /**
     * 消息类型
     */
    private String msgtype;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Customservice getCustomservice() {
        return customservice;
    }

    public void setCustomservice(Customservice customservice) {
        this.customservice = customservice;
    }
}
