package com.sunkang.exception;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：微信公众号异常
 */
public class WeChatException extends Exception{
    public WeChatException() {
        super();
    }

    public WeChatException(String message) {
        super(message);
    }

    public WeChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
