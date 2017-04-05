package com.sunkang.entity.response;

import com.sunkang.entity.response.base.BaseMessage;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应视频消息
 */
public class VideoMessage extends BaseMessage {

    /**
     * 通过素材管理接口上传多媒体文件，得到的id
     */
    private String MediaId;

    /**
     * 视频消息的标题
     */
    private String Title;

    /**
     * 视频消息的描述
     */
    private String Description;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
