package com.sunkang.dao;

import com.sunkang.entity.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 孙康
 * @date 2017/4/25
 * Describe：保存媒体文件信息
 */
@Repository
public class RedisRepository {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String,String> valOpsStr;

    @Autowired
    RedisTemplate<Object,Object > redisTemplate;

    @Resource(name="redisTemplate")
    ValueOperations<Object,Object> valOps;

    /**
     * 像redis里面保存字符串
     * @param key
     * @param value
     */
    public void saveStr(String key,String value){
        valOpsStr.set(key,value);
    }

    /**
     * 获得字符串类型
     * @param key
     * @return
     */
    public String getStr(String key){
        return valOpsStr.get(key);
    }

    /**
     * 保存对象
     * @param key
     * @param object
     */
    public void saveObject(String key,Object object){
        valOps.set(key,object);
    }

    /**
     * 查询对象
     * @param key
     * @return
     */
    public Object getObject(String key){
        return (Object) valOps.get(key);
    }

}
