package com.sunkang.entity;

/**
 * @author 孙康
 * @date 2017/5/4
 * Describe：知识问答子表
 */
public class KnowledgeSub {

    //主键
    private Integer id;

    //问答表id
    private Integer pid;

    //答案
    private String answer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
