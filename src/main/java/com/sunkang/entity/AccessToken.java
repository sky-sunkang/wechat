package com.sunkang.entity;

import java.io.Serializable;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：公众号接口调用唯一凭证
 */
public class AccessToken implements Serializable {


    /**
     * 接口调用凭证
     */
    private String accessToken;

    /**
     * 有效时间
     */
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
