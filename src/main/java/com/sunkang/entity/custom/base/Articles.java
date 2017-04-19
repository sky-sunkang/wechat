package com.sunkang.entity.custom.base;

import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：图文消息model
 */
public class Articles {

    /**
     * 多条图文消息
     */
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
