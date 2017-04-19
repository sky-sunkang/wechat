package com.sunkang.entity.custom;

import com.sunkang.entity.custom.base.Articles;
import com.sunkang.entity.custom.base.BaseCustomMessage;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客服消息：图文消息
 */
public class NewsCustomMessage extends BaseCustomMessage{

    /**
     * 图文内容
     */
    private Articles news;

    public Articles getNews() {
        return news;
    }

    public void setNews(Articles news) {
        this.news = news;
    }
}
