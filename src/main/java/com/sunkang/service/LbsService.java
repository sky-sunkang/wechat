package com.sunkang.service;

import com.sunkang.common.RedisKeyConstants;
import com.sunkang.dao.RedisRepository;
import com.sunkang.entity.lbs.BaiduPlace;
import com.sunkang.entity.lbs.Location;
import com.sunkang.utils.BaiduMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 孙康
 * @date 2017/4/27
 * Describe：周边搜搜服务
 */
@Service
public class LbsService {

    @Autowired
    private RedisRepository redisRepository;

    //处理周边搜索消息
    public String handLbsMessage(Map<String,String> messageMap) throws IOException {
        String content= messageMap.get("Content");//消息内容
        String openId=messageMap.get("FromUserName");//用户id
        //获取位置信息
        Location location= (Location) redisRepository.getObject(RedisKeyConstants.LOCATION+openId);
        //装换坐标
        location=BaiduMapUtils.convertCoord(location.getLng(),location.getLat());
        //检索关键字
        String query=content.substring(2,content.length());
        //获得附近检索信息
        List<BaiduPlace> baiduPlaces=BaiduMapUtils.searchPlace(query,location.getBd09Lng(),location.getBd09Lat());
        //组装图文信息
        String posStr=BaiduMapUtils.makeArticles(baiduPlaces,messageMap.get("ToUserName"),openId);
        return posStr;
    }
}
