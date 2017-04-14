package com.sunkang.controller;

import com.sunkang.entity.AccessToken;
import com.sunkang.service.MenuService;
import com.sunkang.service.TokenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 孙康
 * @date 2017/4/12
 * Describe：菜单控制器
 */
@Controller
public class MenuController {
    private static Logger log=Logger.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private TokenService tokenService;

    /**
     * 创建菜单
     * @return
     */
    @ResponseBody
    @RequestMapping("createMenu")
    public String createMenu(){
        try {
            String menuStr=menuService.createMenuStr();
            String accessToken=tokenService.getAccessToken();
            menuService.createMenu(accessToken,menuStr);
            return "创建菜单成功！";
        } catch (Exception e) {
            log.error("创建菜单失败",e);
            return "创建菜单失败！";
        }
    }

}
