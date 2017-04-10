package com.sunkang.entity.req;

import com.sunkang.entity.req.base.BaseEvent;

/**
 * @author 孙康
 * @date 2017/4/10
 * Describe：点击菜单跳转链接时的事件推送
 */
public class ViewEvent extends BaseEvent{

    /**
     * 事件KEY值，设置的跳转URL
     */
    private String EventKey;

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
