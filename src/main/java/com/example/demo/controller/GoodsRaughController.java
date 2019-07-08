package com.example.demo.controller;

import com.example.demo.entity.GoodsRough;
import com.example.demo.mapper.GoodsRoughMapper;
import com.example.demo.service.GoodsRoughServiceIMPL;
//import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsRaughController {
    @Autowired
    private GoodsRoughServiceIMPL goodsRoughServiceIMPL;
    @Autowired
    private GoodsRoughMapper goodsRoughMapper;

    @GetMapping("goodsRough/findWhateverYouWant") //checked
    public List<GoodsRough> findWhateverYouWant(GoodsRough goodsRough){return goodsRoughServiceIMPL.findWhateverYouWant(goodsRough);}

    @GetMapping("/findPriceBetween/{min}-{max}") //checked
    public List<GoodsRough> findPriceBetween(@PathVariable("min") int min,@PathVariable("max") int max){return goodsRoughServiceIMPL.findPriceBetween(min,max);}

    @PostMapping("/deleteGoodRughByID/{id}") //checked
    public void deleteGoodRughByID(@PathVariable("id") int id){goodsRoughServiceIMPL.deleteGoodRughByID(id);}

    @PostMapping("/updateGoodRough") //checked
    public void updateGoodRough(GoodsRough goodsRough){goodsRoughServiceIMPL.updateGoodRough(goodsRough);} //by primary key

    @PostMapping("/addGoodRough")  //checked
    public void addGoodRough(GoodsRough goodsRough){goodsRoughServiceIMPL.addGoodRough(goodsRough);}

    @GetMapping("/findNewGoodsByDate") //checked
    public List<GoodsRough> findNewGoodsByDate(GoodsRough goodsRough){return goodsRoughServiceIMPL.findNewGoodsByDate(goodsRough);}

    @GetMapping("/findTopFiveByDate")
    public List<GoodsRough> findTopFiveByDate( ){return goodsRoughServiceIMPL.findTopFiveByDate();}

    @GetMapping("/findDistinctBranch")
    public List<GoodsRough> findDistinctBranch(){return goodsRoughServiceIMPL.findDistinctBranch();}
}
