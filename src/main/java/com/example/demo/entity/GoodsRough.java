package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsRough {
    private Integer goodsId;

    private String brand;

    private String goodsName;

    private String goodsPicUrl;

    private Date marketDate;

    private BigDecimal goodsPrice;

    private String lable;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPicUrl() {
        return goodsPicUrl;
    }

    public void setGoodsPicUrl(String goodsPicUrl) {
        this.goodsPicUrl = goodsPicUrl;
    }

    public Date getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(Date marketDate) {
        this.marketDate = marketDate;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}