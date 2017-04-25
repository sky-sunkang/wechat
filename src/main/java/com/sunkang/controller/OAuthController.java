package com.sunkang.controller;

import com.sunkang.entity.OAuthAccessToken;
import com.sunkang.entity.user.UserInfo;
import com.sunkang.service.OAuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * @author 孙康
 * @date 2017/4/19
 * Describe：测试oauth
 */
@Controller
public class OAuthController {

    private static Logger log=Logger.getLogger(OAuthController.class);

    @Autowired
    private OAuthService oAuthService;
    /**
     *  测试OAuth网页授权
     * @return
     */
    @RequestMapping("testOAuth")
    public ModelAndView testOAuth(String code,String  state){
        ModelAndView modelAndView=new ModelAndView("test/testOAuth");
        log.debug("code="+code+",state="+state);
        try {
            if(code==null){
                modelAndView.addObject("error","你没有赋予我该权限");
            }else{
                //通过code换取网页授权access_token（不同于基础模块的access_token），code只能用一次，没用的话5分钟后过期
                OAuthAccessToken oAuthAccessToken=oAuthService.getAccessTokenByCode(code);
                //刷新access_token（如果需要）

                //拉取用户信息(需scope为 snsapi_userinfo)
                UserInfo userInfo=oAuthService.getUserInfo(oAuthAccessToken.getAccess_token(),oAuthAccessToken.getOpenid(),"zh_CN");
                modelAndView.addObject("userInfo",userInfo);
            }


        } catch (IOException e) {
            log.error("获取用户信息出错",e);
            modelAndView.addObject("error","获取用户信息出错");
            return modelAndView;
        }

        return modelAndView;
    }
}
