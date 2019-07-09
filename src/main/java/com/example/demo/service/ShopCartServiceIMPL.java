package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.ShopCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCartServiceIMPL {
    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private OrderInfoServiceIMPL orderInfoServiceIMPL;
    @Autowired
    private GoodsRoughServiceIMPL goodsRoughServiceIMPL;

    public void addShopCart(ShopCart shopCart){
        shopCartMapper.insert(shopCart);
    }

    public void deleteShopCardByBothID(ShopCart shopCart) {

        ShopCartKey shopCartKey=new ShopCartKey();
        shopCartKey.setGoodsDetailId(shopCart.getGoodsDetailId());
        shopCartKey.setUserId(shopCart.getUserId());

        shopCartMapper.deleteByPrimaryKey(shopCartKey);
    }

    public boolean isShopCatr(ShopCart shopCart)
    {
        ShopCartExample shopCartExample=new ShopCartExample();
        ShopCartExample.Criteria criteria = shopCartExample.createCriteria();
        criteria.andGoodsDetailIdEqualTo(shopCart.getGoodsDetailId());
        criteria.andUserIdEqualTo(shopCart.getUserId());

        long count = shopCartMapper.countByExample(shopCartExample);

        if(count == 0) return false;
        else return true;
    }

    public List<ShopCart> findShopCardByUserID(String id){
        ShopCartExample shopCartExample=new ShopCartExample();
        shopCartExample.or().andUserIdEqualTo(id);
        return  shopCartMapper.selectByExample(shopCartExample);
    }

    public boolean buyFromShopCart(ShopCart shopCart,GoodsDetail goodsDetail,int days,String dilivery){
        boolean flag=false;
        OrderInfo newOrder=orderInfoServiceIMPL.buildOrder(shopCart,goodsDetail,days,dilivery);
        if(newOrder==null) return false;
        flag=orderInfoServiceIMPL.addOrderInfo(newOrder);

        //finish order delete shop cart
        shopCartMapper.deleteByPrimaryKey(shopCart);

        return flag;
    }

    public List<GoodsRough> findInfoByShpCart(ShopCart shopCart)
    {
        List<ShopCart> shopcartlist = findShopCardByUserID(shopCart.getUserId());
        List<GoodsRough> goodsRoughlist = new ArrayList<GoodsRough>();
        for(ShopCart myshopCart: shopcartlist)
        {
            goodsRoughlist.add(goodsRoughServiceIMPL.findByGoodsRoughID(myshopCart.getGoodsDetailId()));
        }
        return goodsRoughlist;
    }
}
