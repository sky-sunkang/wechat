package com.sunkang.entity.req;

import com.sunkang.entity.req.base.BaseMessage;

/**
 * Created by sunkang on 2017/4/5.
 * 语音消息
 */
public class ImageMessage extends BaseMessage{

    /**
     * 图片链接
     */
    private String PicUrl;

    /**
     * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
     */
    private String MediaId;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
