package com.sunkang.exception;

/**
 * @author 孙康
 * @date 2017/4/12
 * Describe：菜单异常
 */
public class MenuException extends Exception{
    public MenuException(String message) {
        super(message);
    }

    public MenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuException(Throwable cause) {
        super(cause);
    }
}
