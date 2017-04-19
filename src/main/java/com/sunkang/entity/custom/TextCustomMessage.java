package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.BaseCustomMessage;
import com.sunkang.entity.custom.base.Text;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客服消息：文本消息
 */
public class TextCustomMessage extends BaseCustomMessage{

    /**
     * 消息内容
     */
    private Text content;


    public Text getContent() {
        return content;
    }

    public void setContent(Text content) {
        this.content = content;
    }

}
