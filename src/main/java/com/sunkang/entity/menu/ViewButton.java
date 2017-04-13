package com.sunkang.entity.menu;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：view类型的按钮
 */
public class ViewButton extends Button{

    /**
     * 菜单类型
     */
    private String type;

    /**
     * 菜单链接地址
     */
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
