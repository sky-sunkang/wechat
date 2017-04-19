package com.sunkang.entity.custom;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客服
 */
public class Custom {
    /**
     * 客服账号
     */
    private String kf_account;

    /**
     * 客服账号昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
