package com.sunkang.entity.menu;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：click类型的菜单
 */
public class ClickButton  extends Button{

    /**
     * 按钮类型
     */
    private String type;

    /**
     * 按钮的key值
     */
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
