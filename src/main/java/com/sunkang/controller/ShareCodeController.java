package com.sunkang.controller;

import com.sunkang.exception.WeChatException;
import com.sunkang.service.TokenService;
import com.sunkang.utils.TwoCodeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：分享生成的二维码
 */
@Controller
public class ShareCodeController {

    private static Logger log=Logger.getLogger(ShareCodeController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 分享临时二维码
     * @return
     */
    @RequestMapping("shareTempTwoCode")
    public ModelAndView shareTempTwoCode(){
        ModelAndView modelAndView=new ModelAndView("code/two_code");
        try {
            //获取去token
            String accessToken=tokenService.getAccessToken();
            //获取ticket
            String ticket=TwoCodeUtils.getTicket(accessToken,"100",true);
            modelAndView.addObject("ticket",ticket);
        } catch (WeChatException e) {
            log.error("分享二维码异常",e);
            modelAndView.addObject("error","分享二维码异常，请联系客服！");
        }
        return modelAndView;
    }

    /**
     * 分享临时二维码
     * @return
     */
    @RequestMapping("shareTwoCode")
    public ModelAndView shareTwoCode(){
        ModelAndView modelAndView=new ModelAndView("code/two_code");
        try {
            //获取去token
            String accessToken=tokenService.getAccessToken();
            //获取ticket
            String ticket=TwoCodeUtils.getTicket(accessToken,"100",false);
            modelAndView.addObject("ticket",ticket);
        } catch (WeChatException e) {
            log.error("分享二维码异常",e);
            modelAndView.addObject("error","分享二维码异常，请联系客服！");
        }
        return modelAndView;
    }
}
