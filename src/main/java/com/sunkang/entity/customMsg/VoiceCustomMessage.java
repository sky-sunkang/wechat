package com.sunkang.entity.customMsg;

import com.sunkang.entity.customMsg.base.BaseCustomMessage;
import com.sunkang.entity.customMsg.base.Voice;

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
