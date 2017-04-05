package com.sunkang.utils;

/**
 * Created by sunkang on 2017/4/5.
 * byte数组与字符串相互转换
 */
public class BytesStringUtils {

    /**
     * 将byte数组转换为16进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToStr(byte[] bytes){
        String str="";
        for(byte bt:bytes){
            str+=byteToStr(bt);
        }
        return str;
    }

    /**
     * 将byte转换成16进制字符串
     * @param b
     * @return
     */
    public static String byteToStr(byte b){
        char[] digit={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] ob=new char[2];
        ob[0]=digit[(b>>>4)&0X0F];
        ob[1]=digit[b&0X0F];
        return new String(ob);
    }
}
