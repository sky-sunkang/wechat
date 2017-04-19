package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.BaseCustomMessage;
import com.sunkang.entity.custom.base.Mpnews;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客服消息：图文消息-点击调到图文消息
 */
public class MpnewsCustomMessage extends BaseCustomMessage{

    private Mpnews mpnews;

    public Mpnews getMpnews() {
        return mpnews;
    }

    public void setMpnews(Mpnews mpnews) {
        this.mpnews = mpnews;
    }
}
