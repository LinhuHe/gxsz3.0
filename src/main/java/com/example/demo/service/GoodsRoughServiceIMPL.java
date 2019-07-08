package com.example.demo.service;

import com.example.demo.entity.GoodsRough;
import com.example.demo.entity.GoodsRoughExample;
import com.example.demo.mapper.GoodsRoughMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsRoughServiceIMPL {
    @Autowired
    private GoodsRoughMapper goodsRoughMapper;

    public List<GoodsRough> findWhateverYouWant(GoodsRough goodsRough){
        GoodsRoughExample goodsRoughExample=new GoodsRoughExample();
        GoodsRoughExample.Criteria criteria=goodsRoughExample.createCriteria();
        //构建查询语句--不包括picurl和price
        if(goodsRough.getGoodsId()!=null){
            criteria.andGoodsIdEqualTo(goodsRough.getGoodsId());
        }
        if(goodsRough.getGoodsName()!=null && !"".equals(goodsRough.getGoodsName().trim())){
            criteria.andGoodsNameEqualTo(goodsRough.getGoodsName());
        }
        if(goodsRough.getBrand()!=null && !"".equals(goodsRough.getBrand().trim())){
            criteria.andBrandEqualTo(goodsRough.getBrand());
        }
        if (goodsRough.getLable()!=null && !"".equals(goodsRough.getLable().trim())){
            criteria.andLableEqualTo(goodsRough.getLable());
        }

        return goodsRoughMapper.selectByExample(goodsRoughExample);
    }

    public List<GoodsRough> findPriceBetween(int min, int max){
        GoodsRoughExample goodsRoughExample=new GoodsRoughExample();
        GoodsRoughExample.Criteria criteria=goodsRoughExample.createCriteria();

        criteria.andGoodsPriceBetween(BigDecimal.valueOf(min),BigDecimal.valueOf(max));

        return goodsRoughMapper.selectByExample(goodsRoughExample);

    }

    public void deleteGoodRughByID(int id){

       goodsRoughMapper.deleteByPrimaryKey(id);

    }
    //通过RoughId查询
    public GoodsRough findByGoodsRoughID(int id){

        return goodsRoughMapper.selectByPrimaryKey(id);
    }

    public void updateGoodRough(GoodsRough goodsRough){
        goodsRoughMapper.updateByPrimaryKey(goodsRough);
    }

    public void addGoodRough(GoodsRough goodsRough){
        goodsRoughMapper.insert(goodsRough);
    }
    //返回上市日期小于某一日期的商品的GoodsRough信息
    public List<GoodsRough> findNewGoodsByDate(GoodsRough goodsRough){
        GoodsRoughExample goodsRoughExample=new GoodsRoughExample();
        GoodsRoughExample.Criteria criteria=goodsRoughExample.createCriteria();
        criteria.andMarketDateGreaterThan(goodsRough.getMarketDate());

         return goodsRoughMapper.selectByExample(goodsRoughExample);
    }

    public List<GoodsRough> findTopFiveByDate(){
        return goodsRoughMapper.findTopFiveByDate();
    }

    public List<GoodsRough> findDistinctBranch(){
        GoodsRough goodsRough=new GoodsRough();
        List<GoodsRough> all=this.findWhateverYouWant(goodsRough);
        String[] allBrand=goodsRoughMapper.findDistinctBranch();
        List<GoodsRough> target=new ArrayList<>();

        for(int i=0;i<allBrand.length;i++){
            System.out.println(allBrand[i]+"----");
        }
        int brandNumber=allBrand.length;
        int index=allBrand.length+1;
        GoodsRough tmp=new GoodsRough();
        //取得目标品牌的记录
        for (int i=0;i<brandNumber;i++){
            for(int k=0;k<all.size();k++){
                goodsRough=all.get(k);
                if(goodsRough.getBrand().equals(allBrand[i])){
                    target.add(all.get(k));
                    System.out.println("找到了"+goodsRough.getBrand());
                    break;
                }
            }
        }
        System.out.println(target);
        return target;

    }
}
