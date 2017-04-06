package com.sunkang.test;

/**
 * @author 孙康
 * @date 2017/4/6
 * Describe：工作
 */
public class Work
{
    private String id;

    private String name;

    public Work(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
