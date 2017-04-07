package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应文本消息
 */
public class TextMessage extends BaseMessage{

    /**
     * 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
     */
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
