package com.sunkang.entity.menu;

/**
 * @author 孙康
 * @date 2017/4/11
 * Describe：复合型菜单，也就是含有子菜单的一级菜单
 */
public class ComplexButton extends Button{

    /**
     * 子菜单
     */
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
