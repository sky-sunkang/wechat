package com.sunkang.controller;

import com.sunkang.entity.custom.Custom;
import com.sunkang.service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客户服务
 */
@Controller
public class CustomController {

    @Autowired
    private CustomService customService;

    /**
     * 跳转到客户列表界面
     * @return
     */
    @RequestMapping("toCustomList")
    public ModelAndView toCustomList(){
        //获取用户列表 json字符串
        String custsStr=customService.getKfList();
        ModelAndView modelAndView=new ModelAndView("custom/custom_list");
        modelAndView.addObject("custsStr",custsStr);
        return modelAndView;
    }

    /**
     * 跳转到客户添加界面
     * @return
     */
    @RequestMapping("toCustomAdd")
    public ModelAndView toCustomAdd(){
        ModelAndView modelAndView=new ModelAndView("custom/custom_add");
        return modelAndView;
    }


    /**
     * 添加客户
     * @return
     */
    @RequestMapping("addCustom")
    public ModelAndView addCustom(Custom custom){
        customService.addCustom(custom);
        ModelAndView modelAndView=new ModelAndView("custom/custom_list");
        return modelAndView;
    }
}
