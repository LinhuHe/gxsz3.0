package com.example.demo.service;

import com.example.demo.entity.GoodsDetail;
import com.example.demo.entity.GoodsDetailExample;
import com.example.demo.entity.OrderInfo;
import com.example.demo.mapper.GoodsDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsDetailServiceIMPL {
    @Autowired
    private GoodsDetailMapper goodsDetailMapper;


    //通过goodsDetailid删除
    public void deleteByGoodsDetailByDetailID(int id){
        goodsDetailMapper.deleteByPrimaryKey(id);
    }

    //通过goodsRough删除
    public void deleteByGoodRoughID(int id){
        GoodsDetailExample goodsDetailExample=new GoodsDetailExample();
        goodsDetailExample.or().andGoodsIdEqualTo(id);
    }

    //传入修改后的goodsDetail记录
    public void updateGoodsDetailByDetailID(GoodsDetail goodsDetail){
        goodsDetailMapper.updateByPrimaryKey(goodsDetail);
    }

    //传入1个goodsDetail,空余项为null
    public List<GoodsDetail> findWhateverYouWant(GoodsDetail goodsDetail){
        //GoodsDetail tmp=new GoodsDetail();
        GoodsDetailExample goodsDetailExample=new GoodsDetailExample();
        GoodsDetailExample.Criteria criteria=goodsDetailExample.createCriteria();

        //构造查询
        if (goodsDetail.getColor() != null && !"".equals(goodsDetail.getColor().trim())){
            criteria.andColorEqualTo(goodsDetail.getColor());
        }
        if(goodsDetail.getGoodsDetaiId() != null){
            criteria.andGoodsDetaiIdEqualTo(goodsDetail.getGoodsDetaiId());
        }
        if(goodsDetail.getGoodsId() != null){
            criteria.andGoodsIdEqualTo(goodsDetail.getGoodsId());
        }
        if (goodsDetail.getSize() != null && !"".equals(goodsDetail.getSize().trim())) {
            criteria.andSizeEqualTo(goodsDetail.getSize());
        }
        if (goodsDetail.getStyle() != null && !"".equals(goodsDetail.getStyle().trim())){
            criteria.andStyleEqualTo(goodsDetail.getStyle());
        }

        return goodsDetailMapper.selectByExample(goodsDetailExample);
    }

    //查询库存在某区间
    public List<GoodsDetail> findByStock(Integer min, Integer max){
        GoodsDetailExample goodsDetailExample=new GoodsDetailExample();
        GoodsDetailExample.Criteria criteria=goodsDetailExample.createCriteria();

        criteria.andStockBetween(min,max);
        return goodsDetailMapper.selectByExample(goodsDetailExample);
    }
    public int findGoodsId(Integer goodDetailId){
        GoodsDetail goodsDetail=goodsDetailMapper.selectByPrimaryKey(goodDetailId);
        return goodsDetail.getGoodsId();
    }

    public void updateStock(GoodsDetail goodsDetail)
    {
        goodsDetailMapper.updateStock(goodsDetail);
    }
}
