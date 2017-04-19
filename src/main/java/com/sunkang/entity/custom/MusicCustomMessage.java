package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.BaseCustomMessage;
import com.sunkang.entity.custom.base.Music;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客服消息：音乐消息
 */
public class MusicCustomMessage extends BaseCustomMessage{

    /**
     * 音乐
     */
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
