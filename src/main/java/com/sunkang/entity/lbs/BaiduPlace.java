package com.sunkang.entity.lbs;

/**
 * @author 孙康
 * @date 2017/4/27
 * Describe：百度place信息
 */
public class BaiduPlace implements Comparable<BaiduPlace>{

    /**
     * 名称
     */
    private String name;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 精度
     */
    private String lng;

    /**
     * 纬度
     */
    private String lat;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 距离
     */
    private int distance;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * 距离比较
     * @param baiduPlace
     * @return
     */
    @Override
    public int compareTo(BaiduPlace baiduPlace) {
        return  distance-baiduPlace.getDistance();
    }

    @Override
    public String toString() {
        return "BaiduPlace{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", telephone='" + telephone + '\'' +
                ", distance=" + distance +
                '}';
    }
}
