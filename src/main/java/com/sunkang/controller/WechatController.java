package com.sunkang.controller;

import com.sunkang.utils.BytesStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by sunkang on 2017/4/5.
 * 微信服务控制器
 */
@Controller
@RequestMapping("wechat")
public class WechatController {

    private static Logger logger=Logger.getLogger(WechatController.class);

    /**
     * token验证，get请求是token验证
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public  String tokenCheck(String  signature,String timestamp,String nonce,String echostr){
        logger.debug("开始验证token【"+signature+","+timestamp+","+nonce+","+echostr+"】...");
        try {
            //1）将token(公众平台填的token)、timestamp、nonce三个参数进行字典序排序
            String[] arr=new String[]{"sunkang",timestamp,nonce};
            Arrays.sort(arr);

            //2）将三个参数字符串拼接成一个字符串进行sha1加密
            String content=arr[0].concat(arr[1]).concat(arr[2]);

            MessageDigest md=MessageDigest.getInstance("SHA-1");
            byte[] digest=md.digest(content.getBytes());
            //将byte数据转成字符串
            String ciphertext= BytesStringUtils.bytesToStr(digest);

            //3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
            if(ciphertext!=null &&ciphertext.equals(signature.toUpperCase())){
                //验证成功，返回echostr
                logger.debug("token验证通过...");
                return echostr;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("token验证失败",e);
        }
        logger.error("token验证失败");
        return null;
    }

    /**
     * 微信消息的处理，post请求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String dispose(HttpServletRequest request, HttpServletResponse response){
        logger.debug("微信消息处理开始...");
        return null;
    }
}
