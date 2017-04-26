package com.sunkang.entity;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;

/**
 * @author 孙康
 * @date 2017/4/25
 * Describe：上传的媒体素材,会在微信服务器保存3天时间
 */
public class Media implements Serializable {

    /**
     * 文件类型
     */
    private String type;

    /**
     * 媒体id或缩略图媒体id
     */
    private String mediaId;


    /**
     * 创建时间
     */
    private long createAt;

    /**
     * 素材名称
     */
    private String name;

    /**
     * 文件名称
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
