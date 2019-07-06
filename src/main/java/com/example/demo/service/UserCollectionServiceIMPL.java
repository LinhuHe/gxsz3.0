package com.example.demo.service;

import com.example.demo.entity.GoodsDetail;
import com.example.demo.entity.UserCollectionExample;
import com.example.demo.entity.UserCollectionKey;
import com.example.demo.mapper.UserCollectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCollectionServiceIMPL {
    @Autowired
    private UserCollectionMapper userCollectionMapper;
    @Autowired
    private GoodsDetailServiceIMPL goodsDetailServiceIMPL;

    public void addCollection(UserCollectionKey userCollectionKey){
        /*int goodsdetailid;
        GoodsDetail goodsDetail = new GoodsDetail();
        goodsDetail.setGoodsId(userCollectionKey.getGoodsDetailId()); //这里userCollectionKey.getGoodsDetailId()存的是传来的roughid 所以通过这里来找到detailid并存入
        goodsdetailid = goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail).get(0).getGoodsDetaiId();
        userCollectionKey.setGoodsDetailId(goodsdetailid);*/
        userCollectionMapper.insert(userCollectionKey);
    }

    public void deleteCollect(UserCollectionKey userCollectionKey){
        userCollectionMapper.deleteByPrimaryKey(userCollectionKey);
    }

    public List<UserCollectionKey> findCollectionByUserId(String id){
        UserCollectionExample userCollectionExample=new UserCollectionExample();
        UserCollectionExample.Criteria criteria=userCollectionExample.createCriteria();

        criteria.andUserIdEqualTo(id);

        return userCollectionMapper.selectByExample(userCollectionExample);
    }

    public boolean isliked(UserCollectionKey userCollectionKey)
    {
        UserCollectionExample userCollectionExample=new UserCollectionExample();
        UserCollectionExample.Criteria criteria=userCollectionExample.createCriteria();
        criteria.andGoodsDetailIdEqualTo(userCollectionKey.getGoodsDetailId());
        criteria.andUserIdEqualTo(userCollectionKey.getUserId());
        long count = userCollectionMapper.countByExample(userCollectionExample);
        if(count == 0) return false;
        else return true;
    }


}
