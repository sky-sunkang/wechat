package com.sunkang.test;

import java.net.URLEncoder;

/**
 * @author 孙康
 * @date 2017/4/18
 * Describe：
 */
public class URLEncode {
    public static void main(String[] args) {
        String str=URLEncoder.encode("http://sunkang.wicp.net/wechat/testOAuth");
        System.out.println(str);
    }
}
