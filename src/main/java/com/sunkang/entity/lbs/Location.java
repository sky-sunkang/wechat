package com.sunkang.entity.lbs;

import java.io.Serializable;

/**
 * @author 孙康
 * @date 2017/4/27
 * Describe：保存用户位置信息
 */
public class Location implements Serializable{

    /**
     * 用户微信openId
     */
    private String openId;

    /**
     * 地理位置经度
     */
    private String lng;

    /**
     *地理位置维度
     */
    private String lat;

    /**
     * 百度经度
     */
    private String bd09Lng;

    /**
     * 百度纬度
     */
    private String bd09Lat;

    /**
     *地图缩放大小
     */
    private String Scale;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getBd09Lng() {
        return bd09Lng;
    }

    public void setBd09Lng(String bd09Lng) {
        this.bd09Lng = bd09Lng;
    }

    public String getBd09Lat() {
        return bd09Lat;
    }

    public void setBd09Lat(String bd09Lat) {
        this.bd09Lat = bd09Lat;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }

    @Override
    public String toString() {
        return "Location{" +
                "openId='" + openId + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", bd09Lng='" + bd09Lng + '\'' +
                ", bd09Lat='" + bd09Lat + '\'' +
                ", Scale='" + Scale + '\'' +
                '}';
    }
}
