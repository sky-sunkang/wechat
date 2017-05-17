package com.sunkang.dao;

import com.sunkang.entity.Joke;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 孙康
 * @date 2017/5/4
 * Describe：笑话的dao
 */
@Mapper
public interface JokeRepository {

    /**
     * 随机获得一条笑话
     * @return
     */
    Joke getJoke();
}
