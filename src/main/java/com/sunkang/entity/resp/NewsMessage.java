package com.sunkang.entity.resp;

import com.sunkang.entity.resp.base.Articles;

import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：响应图文消息
 */
public class NewsMessage {

    /**
     * 图文消息个数
     */
    private String ArticleCount;

    /**
     * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
     */
    private List<Articles> Articles;

    public String getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(String articleCount) {
        ArticleCount = articleCount;
    }

    public List<com.sunkang.entity.resp.base.Articles> getArticles() {
        return Articles;
    }

    public void setArticles(List<com.sunkang.entity.resp.base.Articles> articles) {
        Articles = articles;
    }
}
