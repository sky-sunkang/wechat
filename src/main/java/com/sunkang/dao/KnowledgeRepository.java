package com.sunkang.dao;

import com.sunkang.entity.Knowledge;

import java.util.List;

/**
 * @author 孙康
 * @date 2017/5/5
 * Describe：问答的数据操作类
 */
public interface KnowledgeRepository {

    /**
     * 获得所有的问答
     * @return
     */
    public List<Knowledge> findAllKnowledge();
}
