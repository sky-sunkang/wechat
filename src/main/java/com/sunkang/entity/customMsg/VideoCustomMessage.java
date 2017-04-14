package com.sunkang.entity.customMsg;

import com.sunkang.entity.customMsg.base.BaseCustomMessage;
import com.sunkang.entity.customMsg.base.Video;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客服消息：视频消息
 */
public class VideoCustomMessage extends BaseCustomMessage {

    /**
     * 视频
     */
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
