package com.sunkang.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sunkang.dao.AccessTokenRepository;
import com.sunkang.entity.custom.Custom;
import com.sunkang.utils.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/17
 * Describe：客户服务
 */
@Service
public class CustomService {

    private static Logger log=Logger.getLogger(CustomService.class);

    @Autowired
    private TokenService tokenService;

    /**
     * 获取客服列表
     * @return
     */
    public String getKfList(){
        InputStream is=null;
        try {
            String path="https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=%s";
            //获取token
            String accessToken=tokenService.getAccessToken();
            path=String.format(path,accessToken);

            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            is=urlConnection.getInputStream();

            return IOUtils.toString(is,"UTF-8");
        } catch (Exception e) {
            log.error("获取客户列表异常",e);

        }finally {
            if (is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void addCustom(Custom custom){
        OutputStream os=null;
        InputStream is=null;
        try {
            String path="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";
            //获取token
            String accessToken=tokenService.getAccessToken();
            path=String.format(path,accessToken);

            URL url=new URL(path);
            URLConnection urlConnection=url.openConnection();
            urlConnection.setDoOutput(true);
            os=urlConnection.getOutputStream();
            String customJson= JsonUtils.object2Json(custom, JsonInclude.Include.NON_NULL);
            os.write(customJson.getBytes("utf-8"));
            os.flush();
            is=urlConnection.getInputStream();
            log.debug(IOUtils.toString(is,"UTF-8"));
        } catch (Exception e) {
            log.error("获取客户列表异常",e);

        }finally {
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
