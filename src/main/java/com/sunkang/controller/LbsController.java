package com.sunkang.controller;

import com.sunkang.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 孙康
 * @date 2017/5/2
 * Describe：周边搜索
 */
@Controller
public class LbsController {

    /**
     * 跳转到附近地图
     * @return
     */
    @RequestMapping("toLbsMap")
    public ModelAndView toLbsMap(String p1,String p2){
        ModelAndView modelAndView=new ModelAndView("lbs/lbs");
        modelAndView.addObject("ak", Constants.BAIDU_AK);
        modelAndView.addObject("p1",p1);
        modelAndView.addObject("p2",p2);
        return modelAndView;
    }
}
