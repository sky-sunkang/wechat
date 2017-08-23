package com.sunkang.service;

import com.sunkang.common.Constants;
import com.sunkang.entity.menu.*;
import com.sunkang.entity.resp.MusicMessage;
import com.sunkang.entity.resp.NewsMessage;
import com.sunkang.entity.resp.TextMessage;
import com.sunkang.entity.resp.base.Articles;
import com.sunkang.entity.resp.base.Music;
import com.sunkang.exception.MenuException;
import com.sunkang.utils.XmlObjectUtils;
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
import java.util.*;

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
        ClickButton but11=new ClickButton();
        but11.setName("ITeay");
        but11.setKey("V1001_ITEAY");
        but11.setType("click");

        ViewButton but12=new ViewButton();
        but12.setName("开源中国");
        but12.setUrl("http://www.oschina.net/");
        but12.setType("view");

        ViewButton but13=new ViewButton();
        but13.setName("孙康的社区");
        but13.setUrl("http://120.77.177.138/wechat/toSKCommunity");
        but13.setType("view");

        ComplexButton but1=new ComplexButton();
        but1.setName("技术交流");
        but1.setSub_button(new Button[]{but11,but12,but13});

        ViewButton but21=new ViewButton();
        but21.setName("分享临时二维码");
        but21.setType("view");
        but21.setUrl("http://120.77.177.138/wechat/shareTempTwoCode");

        ViewButton but22=new ViewButton();
        but22.setName("分享永久二维码");
        but22.setType("view");
        but22.setUrl("http://120.77.177.138/wechat/shareTwoCode");

        ComplexButton but2=new ComplexButton();
        but2.setName("分享公众号");
        but2.setSub_button(new Button[]{but21,but22});

        ViewButton but3=new ViewButton();
        but3.setName("授权");
        but3.setType("view");
        but3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8790dca489d4979a&redirect_uri=http%3A%2F%2Fsunkang.wicp.net%2Fwechat%2FtestOAuth&response_type=code&scope=snsapi_userinfo&state=112#wechat_redirect");


        //创建菜单
        Menu menu=new Menu();
        menu.setButton(new Button[]{but1,but2,but3});
        String menuStr=JSONObject.fromObject(menu).toString();
        log.debug("菜单json字符串为："+menuStr);
        return  menuStr;
    }

    /**
     * 处理菜单点击事件
     * @param messageMap
     * @return
     */
    public String handelClick(Map<String,String> messageMap){
        String eventKey=messageMap.get("EventKey");//事件KEY值，与自定义菜单接口中KEY值对应

        switch (eventKey){
            case "V1001_ITEAY" ://开源中国
                Articles articles=new Articles();
                articles.setUrl("ITeye");
                articles.setUrl("http://www.iteye.com/");
                articles.setDescription("ITeye即创办于2003年9月的javaEye，缘起是创始人范凯自己在学习和研究java的开源框架却发现没有一个讨论的地方，于是自己就办一个。2003年12月范凯开始采取比较严格的管理制度。新用户注册时需要强制做题。做13道有关论坛规则的选择题，做不对就不予审核通过。 2010年9月，javaEye被CSDN低调并购，成为其旗下程序员深度交流社区。后由于Oracle公司不准其网站使用JAVA字样，并提出了苛刻条件，JavaEye网站在交涉无效后，不得不做出更名的决定，于2011年4月1日起，正式更名为ItEye技术网站。");
                articles.setPicUrl("");

                List<Articles> list=new ArrayList<>();
                list.add(articles);

                NewsMessage newsMessage=new NewsMessage();
                newsMessage.setToUserName(messageMap.get("FromUserName"));
                newsMessage.setFromUserName(messageMap.get("ToUserName"));
                newsMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_NEWS);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setArticles(list);
                newsMessage.setArticleCount(list.size());

                Map<String,Class> alias=new HashMap<>();
                alias.put("xml",NewsMessage.class);
                alias.put("item",Articles.class);
                return XmlObjectUtils.object2Xml(newsMessage,alias);
            case "V1001_GOD":
                //响应一个文本消息
                TextMessage textMessage=new TextMessage();
                textMessage.setToUserName(messageMap.get("FromUserName"));
                textMessage.setFromUserName(messageMap.get("ToUserName"));
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
                textMessage.setContent("谢谢你的赞！");

                //转换消息为需要的xml格式
                alias=new HashMap<>();
                alias.put("xml",TextMessage.class);
                return XmlObjectUtils.object2Xml(textMessage,alias);
        }

        return null;
    }
}
