package com.example.demo.controller;

import com.example.demo.entity.GoodsRough;
import com.example.demo.entity.OrderInfo;
import com.example.demo.entity.ShopCart;
import com.example.demo.service.ShopCartServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopCartController {
    @Autowired
    private ShopCartServiceIMPL shopCartServiceIMPL;

    @PostMapping("/addShopCart")  //checked
    public void addShopCart(ShopCart shopCart){shopCartServiceIMPL.addShopCart(shopCart);}

    @PostMapping("/deleteShopCartByBothID") //checked
    public void deleteShopCardByBothID(ShopCart shopCart){shopCartServiceIMPL.deleteShopCardByBothID(shopCart);}

    @GetMapping("/findShopCardByUserID/{id}")  //checked
    public List<ShopCart> findShopCardByUserID(@PathVariable("id") String id){return shopCartServiceIMPL.findShopCardByUserID(id);}

    @PostMapping("/buyFromShopCart")  //uncheck
    public boolean buyFromShopCart(ShopCart shopCart,int days,String dilivery){return shopCartServiceIMPL.buyFromShopCart(shopCart,days,dilivery);}

    @GetMapping("/isShopCart")
    public boolean isShopCatr(ShopCart shopCart){return shopCartServiceIMPL.isShopCatr(shopCart);}

    @GetMapping("/test")
    public void test(ShopCart shopCart,int day,String str)
    {
        System.out.println(shopCart.toString()+" day: "+day+" str: "+str);
    }

    @GetMapping("/findInfoByShopCart")
    public List<GoodsRough> findInfoByShpCart(ShopCart shopCart){return shopCartServiceIMPL.findInfoByShpCart(shopCart);}
}
