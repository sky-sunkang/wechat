package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;
import com.sunkang.entity.resp.base.Voice;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应语音消息
 */
public class VoiceMessage extends BaseMessage{

    /**
     * 语音消息
     */
    private Voice Voice;

    public com.sunkang.entity.resp.base.Voice getVoice() {
        return Voice;
    }

    public void setVoice(com.sunkang.entity.resp.base.Voice voice) {
        Voice = voice;
    }
}
