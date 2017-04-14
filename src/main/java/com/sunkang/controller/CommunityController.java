package com.sunkang.controller;

import com.sunkang.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 孙康
 * @date 2017/4/13
 * Describe：孙康社区的控制器
 */
@Controller
public class CommunityController {

    private static Logger logger=Logger.getLogger(CommunityController.class);
    /**
     * 跳转到孙康的社区界面
     * @return
     */
    @RequestMapping("toSKCommunity")
    public ModelAndView toSKCommunity(){
        TestThread testThread=new TestThread();
        testThread.start();
        return new ModelAndView("community/sKCommunity");
    }
}
class TestThread extends Thread{
    public void run() {
        for(int i=0;i<=10;i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i+" 哈哈哈哈");
        }
    }
}