package com.sunkang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：时间工具类
 */
public class DateUtils {

    /**
     * 将时间戳装换成字符串
     * @param time 要转换的时间
     * @param format 转换格式
     * @return
     */
    public static String time2String(long time,String format){
        Date date=new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }
}
