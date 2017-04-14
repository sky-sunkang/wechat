package com.sunkang.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 孙康
 * @date 2017/4/14
 * Describe：json的工具类
 */
public class JsonUtils {

    /**
     * 对象装换成实体类
     * @param object 要转换的对象
     * @param include 属性的NULL或者""是否转换
     * @return 装换后的json字符串
     */
    public static String object2Json(Object object, JsonInclude.Include include) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        if(include !=null){
            //Include.Include.ALWAYS 默认
            //Include.NON_DEFAULT 属性为默认值不序列化
            //Include.NON_EMPTY 属性为 空（“”） 或者为 NULL 都不序列化
            //Include.NON_NULL 属性为NULL 不序列化
            objectMapper.setSerializationInclusion(include);
        }
        return objectMapper.writeValueAsString(object);
    }
}
