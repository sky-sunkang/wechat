package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应图片消息
 */
public class ImageMessage extends BaseMessage{

    /**
     * 通过素材管理接口上传多媒体文件，得到的id。
     */
    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
}
