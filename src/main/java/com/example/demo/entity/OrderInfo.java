package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Integer orderId;

    private Integer amount;

    private String delivery;

    private Integer goodsDetailId;

    private Date orderDate;

    private BigDecimal orderPrice;

    private Integer status;

    private Date leaseTerm;

    private String userId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(Date leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}