package com.sunkang.entity.response;

import com.sunkang.entity.response.base.Music;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应音乐消息
 */
public class MusicMessage {

    /**
     * 音乐
     */
    private Music Music;

    public com.sunkang.entity.response.base.Music getMusic() {
        return Music;
    }

    public void setMusic(com.sunkang.entity.response.base.Music music) {
        Music = music;
    }
}
