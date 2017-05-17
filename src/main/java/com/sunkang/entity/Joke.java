package com.sunkang.entity;

/**
 * @author 孙康
 * @date 2017/5/4
 * Describe：笑话
 */
public class Joke {

    //主键
    private Integer jokeId;

    //笑话内容
    private String jokeContent;

    public Integer getJokeId() {
        return jokeId;
    }

    public void setJokeId(Integer jokeId) {
        this.jokeId = jokeId;
    }

    public String getJokeContent() {
        return jokeContent;
    }

    public void setJokeContent(String jokeContent) {
        this.jokeContent = jokeContent;
    }
}
