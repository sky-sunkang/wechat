package com.sunkang.entity;

/**
 * @author 孙康
 * @date 2017/5/4
 * Describe：知识问答实体
 */
public class Knowledge {

    //主键
    private Integer id;

    //问题
    private String question;

    //答案
    private String answer;

    //知识类别
    private Integer category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
