package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;
import com.sunkang.entity.resp.base.Video;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应视频消息
 */
public class VideoMessage extends BaseMessage {

    /**
     * 视频
     */
    private Video Video;

    public com.sunkang.entity.resp.base.Video getVideo() {
        return Video;
    }

    public void setVideo(com.sunkang.entity.resp.base.Video video) {
        Video = video;
    }
}
