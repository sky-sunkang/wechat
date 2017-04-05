package com.sunkang.entity.request;

import com.sunkang.entity.request.base.BaseMessage;

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
