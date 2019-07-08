package com.example.demo.entity;

public class GoodsDetail {
    private Integer goodsDetaiId;

    private Integer goodsId;

    private String color;

    private String size;

    private Integer stock;

    private String style;

    public Integer getGoodsDetaiId() {
        return goodsDetaiId;
    }

    public void setGoodsDetaiId(Integer goodsDetaiId) {
        this.goodsDetaiId = goodsDetaiId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public String toString() {
        return "GoodsDetail{" +
                "goodsDetaiId=" + goodsDetaiId +
                ", goodsId=" + goodsId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", stock=" + stock +
                ", style='" + style + '\'' +
                '}';
    }
}