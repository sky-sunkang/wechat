package com.sunkang.entity.customMsg;

import com.sunkang.entity.customMsg.base.BaseCustomMessage;
import com.sunkang.entity.customMsg.base.Image;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：客服消息：图片消息
 */
public class ImageCustomMessage extends BaseCustomMessage{

    /**
     * 图片消息
     */
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
