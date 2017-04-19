package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.BaseCustomMessage;
import com.sunkang.entity.custom.base.Wxcard;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客服消息：微信卡券
 */
public class WxcardCustomMessage extends BaseCustomMessage{

    /**
     * 微信卡券
     */
    private Wxcard wxcard;

    public Wxcard getWxcard() {
        return wxcard;
    }

    public void setWxcard(Wxcard wxcard) {
        this.wxcard = wxcard;
    }
}
