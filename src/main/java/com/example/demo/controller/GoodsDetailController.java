package com.example.demo.controller;

import com.example.demo.entity.GoodsDetail;
import com.example.demo.service.GoodsDetailServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsDetailController {

    @Autowired
    private GoodsDetailServiceIMPL goodsDetailServiceIMPL;

    @PostMapping("/deleteByGoodsDetailByDetailID/{id}") //checked
    public void deleteByGoodsDetailByDetailID(@PathVariable("id") int id){goodsDetailServiceIMPL.deleteByGoodsDetailByDetailID(id);}

    @PostMapping("/deleteByGoodRoughID/{id}")  //not complited?
    public void deleteByGoodRoughID(@PathVariable("id") int id){goodsDetailServiceIMPL.deleteByGoodRoughID(id);}

    @PostMapping("/updateGoodsDetailByDetailID") //checked
    public void updateGoodsDetailByDetailID(GoodsDetail goodsDetail){ goodsDetailServiceIMPL.updateGoodsDetailByDetailID(goodsDetail); }

    @GetMapping("/goodsDetail/findWhateverYouWant") //checked
    public List<GoodsDetail> findWhateverYouWant(GoodsDetail goodsDetail){
        System.out.println("doing findWhateverYouWant "+"did:"+goodsDetail.getGoodsDetaiId()+"  gid:"+goodsDetail.getGoodsId()+" color:"+goodsDetail.getColor()+" style:"+goodsDetail.getStyle()+" stoke:"+goodsDetail.getStock()+" size:"+goodsDetail.getSize());
        return goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail);
    }

    @GetMapping("/findByStock/{min}-{max}")  //checked
    public List<GoodsDetail> findByStock(@PathVariable("min") Integer min,@PathVariable("max") Integer max){return goodsDetailServiceIMPL.findByStock(min ,max);}

}
