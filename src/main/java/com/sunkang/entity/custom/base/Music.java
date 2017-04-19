package com.sunkang.entity.custom.base;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：音乐model
 */
public class Music {

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 音乐链接
     */
    private String mucicurl;

    /**
     * 高品质音乐链接
     */
    private String hqmusicurl;

    /**
     * 简略图媒体id
     */
    private String thumb_media_id;

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

    public String getMucicurl() {
        return mucicurl;
    }

    public void setMucicurl(String mucicurl) {
        this.mucicurl = mucicurl;
    }

    public String getHqmusicurl() {
        return hqmusicurl;
    }

    public void setHqmusicurl(String hqmusicurl) {
        this.hqmusicurl = hqmusicurl;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }
}
