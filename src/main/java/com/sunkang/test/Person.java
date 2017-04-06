package com.sunkang.test;

import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/5
 * Describe：人的实体类，测试XStream
 */
public class Person {
    private String id;

    private String name;

    private Integer age;

    private String[] address;

    private Work oneWork;

    private List<Work> otherWorks;

    public Person(String id, String name, Integer age, String[] address, Work oneWork, List<Work> otherWorks) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.oneWork = oneWork;
        this.otherWorks = otherWorks;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public Work getOneWork() {
        return oneWork;
    }

    public void setOneWork(Work oneWork) {
        this.oneWork = oneWork;
    }

    public List<Work> getOtherWorks() {
        return otherWorks;
    }

    public void setOtherWorks(List<Work> otherWorks) {
        this.otherWorks = otherWorks;
    }
}
