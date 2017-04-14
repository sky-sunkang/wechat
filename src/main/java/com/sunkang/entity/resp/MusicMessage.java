package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;
import com.sunkang.entity.resp.base.Music;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应音乐消息
 */
public class MusicMessage extends BaseMessage{

    /**
     * 音乐
     */
    private Music Music;

    public com.sunkang.entity.resp.base.Music getMusic() {
        return Music;
    }

    public void setMusic(com.sunkang.entity.resp.base.Music music) {
        Music = music;
    }
}
