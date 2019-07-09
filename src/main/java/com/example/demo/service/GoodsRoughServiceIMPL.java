package com.example.demo.service;

import com.example.demo.entity.GoodRoughAndDetail;
import com.example.demo.entity.GoodsDetail;
import com.example.demo.entity.GoodsRough;
import com.example.demo.entity.GoodsRoughExample;
import com.example.demo.mapper.GoodsRoughMapper;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GoodsRoughServiceIMPL {
    @Autowired
    private GoodsRoughMapper goodsRoughMapper;
    @Autowired
    private GoodsDetailServiceIMPL goodsDetailServiceIMPL;

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

    public List<GoodsRough> findByBrand(GoodsRough goodsRough){

        return findWhateverYouWant(goodsRough);
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
            //System.out.println(allBrand[i]+"----");
        }
        int brandNumber=allBrand.length;
        int index=allBrand.length+1;
        GoodsRough tmp=new GoodsRough();
        //取得目标品牌的记录
       /* for (int i=0;i<3;i++){
            for(int k=0;k<3;k++){
                goodsRough=all.get(k);
                if(goodsRough.getBrand().equals(allBrand[i])){
                    target.add(all.get(k));
                    System.out.println("找到了"+goodsRough.getBrand());
                    break;
                }
            }
        }*/
        for (int i=0;i<brandNumber;i++){
            for(int k=0;k<all.size();k++){
                goodsRough=all.get(k);
                if(goodsRough.getBrand().equals(allBrand[i])){
                    target.add(all.get(k));
                    //System.out.println("找到了"+goodsRough.getBrand());
                    break;
                }
            }
        }
        //System.out.println(target);

        return target.subList(0,4);
    }

    public List<GoodsRough> findByRoughAndDetail(GoodsRough goodsRough,GoodsDetail goodsDetail) //goodsRough contains brand and lable/ goodsDetail contains style size
    {
        List<GoodsRough> roughlist = findWhateverYouWant(goodsRough);
        List<GoodsDetail> detaillist =goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail);
        if(roughlist.size()<=0||detaillist.size()<=0) return null;
        //List<GoodRoughAndDetail> target = new ArrayList<GoodRoughAndDetail>();
        List<GoodsRough> target = new ArrayList<GoodsRough>();
        for(GoodsRough gsr:roughlist)
        {
           for(GoodsDetail gsd:detaillist)
           {
               if(gsr.getGoodsId().equals(gsd.getGoodsId())&& gsd.getStock()>0) //搜索条件吻合 且有库存
               {
                   //GoodRoughAndDetail goodRoughAndDetail = new GoodRoughAndDetail();
                   target.add(gsr);
                   break;
               }
           }
        }

        return target;
    }

    public List<GoodsRough> RandGiveYou()
    {
        HashMap map = new HashMap();
        Random rad = new Random();
        List<GoodsRough> gsrlis = findWhateverYouWant(new GoodsRough());
        List<GoodsRough> target = new ArrayList<>();
        while(map.size()<10)
        {
            int num = rad.nextInt(gsrlis.size()-1);
            if(map.containsKey(num)) {}
            else
            {
             map.put(num,num);
            }

        }

        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry= (Map.Entry) iter.next();
            target.add(gsrlis.get((int)entry.getValue()));
        }


        return target;
    }

    public GoodsRough findLowestPrice(){return goodsRoughMapper.findLowestPrice();}

    public List<GoodsRough> unicodeRecommed()
    {
        List<GoodsRough> gsrlist = new LinkedList<>();

        //least stock
        gsrlist.add(goodsDetailServiceIMPL.findLeastStock());
        //newest
        gsrlist.add(findTopFiveByDate().get(0));
        //lowest price
        gsrlist.add(findLowestPrice());

        return gsrlist;
    }
}
