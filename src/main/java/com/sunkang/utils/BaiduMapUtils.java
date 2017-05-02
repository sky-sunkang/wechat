package com.sunkang.utils;

import com.sunkang.common.Constants;
import com.sunkang.entity.lbs.BaiduPlace;
import com.sunkang.entity.lbs.Location;
import com.sunkang.entity.resp.NewsMessage;
import com.sunkang.entity.resp.base.Articles;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author 孙康
 * @date 2017/4/27
 * Describe：百度地图工具类
 */
public class BaiduMapUtils {

    private static Logger log=Logger.getLogger(BaiduMapUtils.class);

    private static String searchPlacePath="http://api.map.baidu.com/place/v2/search?query=QUERY&location=LAT,LNG&redius=2000&output=json&page_size=8&page_num=0&scope=2&ak=AK";

    //坐标转换接口
    private static String convertPath="http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
    /**
     * 检索附近
     * @param query 关键字
     * @param lng 经度
     * @param lat 纬度
     * @return
     * @throws IOException
     */
    public static List<BaiduPlace> searchPlace(String query, String lng, String lat) throws IOException {
        //根据关键字、精度、维度查询检索附近
        String reqUrl=searchPlacePath.replace("QUERY", URLEncoder.encode(query,"UTF-8"))
                .replace("LAT",lat)
                .replace("LNG",lng)
                .replace("AK", Constants.BAIDU_AK);
        //调用百度place api圆形检索
        String posJson=httpRequest(reqUrl);
        log.debug(posJson);
        //解析xml为baiduplace
        List<BaiduPlace>  baiduPlaces=parsePlaceJson(posJson);
        return baiduPlaces;
    }

    /**
     * 发送请求，获取返回值
     * @param reqUrl 请求地址
     * @return
     * @throws IOException
     */
    public static String httpRequest(String reqUrl) throws IOException {
        URL url=new URL(reqUrl);
        URLConnection urlConnection=url.openConnection();
        InputStream is=urlConnection.getInputStream();
        String posXml= IOUtils.toString(is,"utf-8");
        is.close();
        return posXml;
    }

    /**
     * 解析json为place对象
     * @param jsonStr 要解析的json字符串
     * @return
     */
    public static List<BaiduPlace> parsePlaceJson(String jsonStr){
        List<BaiduPlace> baiduPlaces=new ArrayList<>();
        JSONObject jsonObject=JSONObject.fromObject(jsonStr);
        JSONArray resultObj=jsonObject.getJSONArray("results");
        for(int i=0;i<resultObj.size();i++){
            BaiduPlace baiduPlace=new BaiduPlace();

            JSONObject placeJson=resultObj.getJSONObject(i);
            String name=placeJson.getString("name");//名称
            JSONObject locationJson=placeJson.getJSONObject("location");
            String lat=locationJson.getString("lat");//纬度
            String lng=locationJson.getString("lng");//经度
            String address=placeJson.getString("address");//地址

            //将可能没有电话或者详细信息的错误捕捉
            try {
                //详细信息
                JSONObject detailJson=placeJson.getJSONObject("detail_info");//详细
                int distance=detailJson.getInt("distance");//距离
                baiduPlace.setDistance(distance);
                String telephone=placeJson.getString("telephone");//电话
                baiduPlace.setTelephone(telephone);
            }catch (Exception e){
                log.error("解析json出错",e);
            }
            baiduPlace.setAddress(address);
            baiduPlace.setName(name);
            baiduPlace.setLat(lat);
            baiduPlace.setLng(lng);
            baiduPlaces.add(baiduPlace);
        }
        return baiduPlaces;
    }

    /**
     * 将微信的坐标转换成百度坐标（GCJ-02 -> Baidu）
     * @param lng 经度
     * @param lat 纬度
     * @return
     */
    public static Location convertCoord(String lng,String lat) throws IOException {
        String reqUrl=convertPath.replace("{x}",lng).replace("{y}",lat);
        String posJson=httpRequest(reqUrl);
        //解析响应
        Location location=new Location();
        JSONObject jsonObject=JSONObject.fromObject(posJson);
        //base64解码
        location.setBd09Lng(new String( Base64.getDecoder().decode(jsonObject.getString("x").getBytes("utf-8")),"utf-8"));
        location.setBd09Lat(new String( Base64.getDecoder().decode(jsonObject.getString("y").getBytes("utf-8")),"utf-8"));
        return location;
    }

    /**
     * 将附近搜索组装成图文消息
     * @param baiduPlaces
     * @param formUser
     * @param toUser
     * @param bd09lng
     * @param bd09lat
     * @return
     */
    public static String  makeArticles(List<BaiduPlace> baiduPlaces,String formUser,String toUser,String bd09lng,String bd09lat){
        //将List排序，升序
        baiduPlaces.sort(BaiduPlace::compareTo);
        List<Articles> list=new ArrayList<>();
        for(int i=0;i<baiduPlaces.size();i++){
            BaiduPlace baiduPlace=baiduPlaces.get(i);
            Articles articles=new Articles();

            String phone=baiduPlace.getTelephone()==null?"无电话":baiduPlace.getTelephone();
            String title=baiduPlace.getName()+"  "+baiduPlace.getDistance()+"米"
                    +"\n电话："+phone
                    +"\n地址："+baiduPlace.getAddress();
            articles.setTitle(title);
            String url=Constants.DOMIN_NAME+"/toLbsMap?p1=%s,%s&p2=%s,%s";
            url=String.format(url,bd09lng,bd09lat,baiduPlace.getLng(),baiduPlace.getLat());
            log.debug("附近搜索url："+url);
            articles.setUrl(url);
            if(i==0){
                articles.setPicUrl(Constants.DOMIN_NAME+"/image/lbs_big.png");
            }else {
                articles.setPicUrl(Constants.DOMIN_NAME+ "/image/lbs_sm.png");
            }
            list.add(articles);
        }

        NewsMessage newsMessage=new NewsMessage();
        newsMessage.setFromUserName(formUser);
        newsMessage.setToUserName(toUser);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setArticles(list);
        newsMessage.setArticleCount(list.size());
        newsMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_NEWS);

        //转换成返回的xml消息
        Map<String,Class> alias =new HashMap<>();
        alias.put("xml",NewsMessage.class);
        alias.put("item",Articles.class);
        return XmlObjectUtils.object2Xml(newsMessage,alias);
    }

    public static void main(String[] args) throws IOException {
        List<BaiduPlace> baiduPlaces=searchPlace("酒店","114.090102","22.547221");
        log.debug(baiduPlaces);
        Location location=convertCoord("22.544094","114.078362");
        log.debug(location.toString());
    }

}
