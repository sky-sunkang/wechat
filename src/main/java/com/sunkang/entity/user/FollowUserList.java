package com.sunkang.entity.user;

import java.util.List;

/**
 * @author 孙康
 * @date 2017/4/20
 * Describe：关注用户列表
 */
public class FollowUserList {
    /**
     * 总关注人数
     */
    private int total;

    /**
     * 获取openid的个数
     */
    private int count;

    /**
     * openId列表
     */
    private List<String> openIds;

    /**
     * 拉取的最后一个用户的openId
     */
    private String nextOpenId;

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getOpenIds() {
        return openIds;
    }

    public void setOpenIds(List<String> openIds) {
        this.openIds = openIds;
    }

    @Override
    public String toString() {
        return "FollowUserList{" +
                "total=" + total +
                ", count=" + count +
                ", openIds=" + openIds +
                ", nextOpenId='" + nextOpenId + '\'' +
                '}';
    }
}
