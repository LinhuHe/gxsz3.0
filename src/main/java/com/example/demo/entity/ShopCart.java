package com.example.demo.entity;

public class ShopCart extends ShopCartKey {
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ShopCart{" +
                "amount=" + amount +
                '}';
    }
}