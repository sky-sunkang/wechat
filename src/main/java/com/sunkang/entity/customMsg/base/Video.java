package com.sunkang.entity.customMsg.base;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：视频消息model
 */
public class Video {

    /**
     * 媒体id
     */
    private String media_id;

    /**
     * 视频消息缩略图的媒体id
     */
    private String thumb_media_id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
