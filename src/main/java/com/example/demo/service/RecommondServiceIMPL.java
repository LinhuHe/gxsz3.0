package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.GoodsRoughMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RecommondServiceIMPL {

    @Autowired
    private OrderInfoServiceIMPL orderInfoServiceIMPL;
    @Autowired
    private GoodsRoughMapper goodsRoughMapper;
    @Autowired
    private UserInfoServiceIMPL userInfoServiceIMPL;

    public List<OrderInfo> randomRecommand(){
        //现在和过去7天的时间

        Calendar nowC=Calendar.getInstance();
        Calendar pastC=Calendar.getInstance();
        pastC.add(Calendar.DATE,-30);

        Date now=nowC.getTime();
        Date past=pastC.getTime();

        List<OrderInfo> rs=orderInfoServiceIMPL.findOrderBetween(past,now);
        if(rs.isEmpty()){
            return null;
        }
        //不太确定是不是应该减一
        GoodsRoughServiceIMPL goodsRoughServiceIMPL=new GoodsRoughServiceIMPL();
        GoodsDetailServiceIMPL goodsDetailServiceIMPL=new GoodsDetailServiceIMPL();
        GoodsRough goodsRough=new GoodsRough();
        GoodsDetail goodsDetail=new GoodsDetail();
        goodsDetail.setGoodsId(rs.get(0).getGoodsDetailId());
        List<GoodsDetail> d=goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail);


        return null;

    }
    //简单粗暴，推荐最近买的品牌的商品
    public List<GoodsDetail> brandRecommand(String userId){
        //userInfoServiceIMPL.selectUserInfo()
        return null;
    }

    public List<GoodsRough> newGoodsRecommand(){
        //现在和过去30天的时间
        Calendar nowC=Calendar.getInstance();
        Calendar pastC=Calendar.getInstance();
        pastC.add(Calendar.DATE,-30);

        Date now=nowC.getTime();
        Date past=pastC.getTime();
        //查询过去30天上市的新品
        GoodsRoughExample goodsRoughExample=new GoodsRoughExample();
        GoodsRoughExample.Criteria criteria=goodsRoughExample.createCriteria();

        criteria.andMarketDateBetween(past,now);

        return goodsRoughMapper.selectByExample(goodsRoughExample);
    }
}
