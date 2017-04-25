package com.sunkang.entity.user;

/**
 * @author 孙康
 * @date 2017/4/21
 * Describe：客户标签
 */
public class Tag {

    /**
     * 标签id
     */
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签下人数
     */
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
