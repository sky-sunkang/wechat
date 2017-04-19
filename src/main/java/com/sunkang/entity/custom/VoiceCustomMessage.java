package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.BaseCustomMessage;
import com.sunkang.entity.custom.base.Voice;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客服消息：语音消息
 */
public class VoiceCustomMessage extends BaseCustomMessage{
    /**
     * 语音消息
     */
    private Voice voice;

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
