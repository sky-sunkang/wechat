package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.BaseMessage;
import com.sunkang.entity.resp.base.Image;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应图片消息
 */
public class ImageMessage extends BaseMessage{

    /**
     * 图片
     */
    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image Image) {
        this.Image = Image;
    }
}
