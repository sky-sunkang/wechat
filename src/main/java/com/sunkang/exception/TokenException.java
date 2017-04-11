package com.sunkang.exception;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：获取token时的异常
 */
public class TokenException extends Exception{

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
