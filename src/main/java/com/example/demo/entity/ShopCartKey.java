package com.example.demo.entity;

public class ShopCartKey {
    private Integer goodsDetailId;

    private String userId;

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShopCartKey{" +
                "goodsDetailId=" + goodsDetailId +
                ", userId='" + userId + '\'' +
                '}';
    }
}