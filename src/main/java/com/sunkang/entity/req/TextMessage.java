package com.sunkang.entity.req;

import com.sunkang.entity.req.base.BaseMessage;

/**
 * Created by sunkang on 2017/4/5.
 * 文本消息
 */
public class TextMessage extends BaseMessage{

    /**
     * 消息内容
     */
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
