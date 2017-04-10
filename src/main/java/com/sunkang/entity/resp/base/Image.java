package com.sunkang.entity.resp.base;

/**
 * @author 孙康
 * @date 2017/4/10
 * Describe：图片model
 */
public class Image{

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
