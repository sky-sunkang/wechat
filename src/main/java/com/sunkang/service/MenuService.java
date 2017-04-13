package com.sunkang.service;

import com.sunkang.entity.menu.*;
import com.sunkang.exception.MenuException;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 孙康
 * @date 2017/4/12
 * Describe：菜单的服务层
 */
@Service
public class MenuService {

    private Logger log=Logger.getLogger(MenuService.class);

    /**
     * 创建菜单
     * @param accessToken
     * @param menuStr 菜单json字符串
     * @throws Exception
     */
    public void createMenu(String accessToken,String menuStr) throws Exception {
        String path="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
        OutputStream os=null;
        InputStream is=null;
        try {
            //创建链接
            URL url= new URL(path);
            URLConnection connection=url.openConnection();
            connection.setDoOutput(true);
            //传入post参数
            os=connection.getOutputStream();
            os.write(menuStr.getBytes());
            os.flush();
            //获取返回值
            is= connection.getInputStream();
            String outStr= IOUtils.toString(is,"utf-8");
            log.debug("创建菜单结果："+outStr);

            //创建菜单返回信息
            JSONObject jsonObject=JSONObject.fromObject(outStr);
            int errcode=jsonObject.getInt("errcode");
            String errmsg=jsonObject.getString("errmsg");
            if(errcode!=0){
                throw new MenuException("创建菜单错误 ：errcode="+errcode+",errmsg="+errmsg);
            }

        } catch (Exception e) {
            throw new MenuException(e);
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *  拼接菜单字符串
     * @return
     */
    public String createMenuStr(){
        //现写死在这，后期从数据库中查询

        ClickButton but1=new ClickButton();
        but1.setName("今日歌曲");
        but1.setKey("V1001_TODAY_MUSIC");
        but1.setType("click");

        ViewButton but2=new ViewButton();
        but2.setName("歌手简介");
        but2.setType("view");
        but2.setUrl("http://www.baidu.com/");

        ClickButton but31=new ClickButton();
        but31.setName("赞一下");
        but31.setType("click");
        but31.setKey("V1001_GOOD");

        ClickButton but32=new ClickButton();
        but32.setName("hello word");
        but32.setType("click");
        but32.setKey("V1001_HELLO_WORD");

        //包含两个菜单的一级菜单
        ComplexButton but3=new ComplexButton();
        but3.setName("其他操作");
        but3.setSub_button(new Button[]{but31,but32});

        //创建菜单
        Menu menu=new Menu();
        menu.setButton(new Button[]{but1,but2,but3});
        String menuStr=JSONObject.fromObject(menu).toString();
        log.debug("菜单json字符串为："+menuStr);
        return  menuStr;
    }
}
