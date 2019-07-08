package com.example.demo.controller;

import com.example.demo.entity.GoodsDetail;
import com.example.demo.entity.GoodsRough;
import com.example.demo.entity.OrderAndGoodsInfo;
import com.example.demo.entity.OrderInfo;
import com.example.demo.service.GoodsDetailServiceIMPL;
import com.example.demo.service.GoodsRoughServiceIMPL;
import com.example.demo.service.OrderInfoServiceIMPL;
import org.apache.commons.collections.list.GrowthList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class OrderInfoController {
    @Autowired
    private OrderInfoServiceIMPL orderInfoServiceIMPL;
    @Autowired
    private GoodsDetailServiceIMPL goodsDetailServiceIMPL;
    @Autowired
    private GoodsRoughServiceIMPL goodsRoughServiceIMPL;
    @Autowired
    private OrderAndGoodsInfo orderAndGoodsInfo;

    @PostMapping("/updateOrderInfo") //checked
    public void updateOrderInfo(OrderInfo orderInfo){ orderInfoServiceIMPL.updateOrderInfo(orderInfo);} //by primary key

    @PostMapping("/deleteOrderInfo/{id}") //checked
    public void deleteOrderInfo(@PathVariable("id") Integer id){orderInfoServiceIMPL.deleteOrderInfo(id);}

    @PostMapping("/addOrderInfo") //c
    public boolean addOrderInfo(OrderInfo orderInfo){ return orderInfoServiceIMPL.addOrderInfo(orderInfo);}

    @GetMapping("/findOrderByUserID/{id}") //chaecked
    public List<OrderInfo> findOrderByUserID(@PathVariable("id") String id){return orderInfoServiceIMPL.findOrderByUserID(id);}

    @GetMapping("/findOrderByGoodsDetailID/{id}") //checked
    public List<OrderInfo> findOrderByGoodsDetailID(@PathVariable("id") Integer id){return orderInfoServiceIMPL.findOrderByGoodsDetailID(id);}

    @GetMapping("/findOrderByOrderId/{id}") //checked
    public List<OrderInfo> findOrderByOrderId(@PathVariable("id") Integer id){return orderInfoServiceIMPL.findOrderByOrderId(id);}

    @GetMapping("/orderinfo/findWhateverYouWant") //checked
    public List<OrderInfo> findWhateverYouWant(OrderInfo orderInfo){return orderInfoServiceIMPL.findWhateverYouWant(orderInfo);}

    @GetMapping("/findByStatus")
    public int findByStatus(OrderInfo orderInfo){
        List<OrderInfo> list = new ArrayList<OrderInfo>();
        list = orderInfoServiceIMPL.findWhateverYouWant(orderInfo);
        return list.size();
    }
    @GetMapping("/admin/findOrderByStatus") //
    public List<OrderInfo>  findOrderByStatus(@RequestParam ("status") Integer status){
        return orderInfoServiceIMPL.findOrderByStatus(status);
    }
    @GetMapping("/admin/findOrderDetail") //
    public List<GoodsRough>  findOrderDetail(@RequestParam ("status") Integer status){
        List<OrderInfo> orderInfos= orderInfoServiceIMPL.findOrderByStatus(status);
        GoodsDetailServiceIMPL goodsDetailServiceIMPL=new GoodsDetailServiceIMPL();
        GoodsRoughServiceIMPL goodsRoughServiceIMPL=new GoodsRoughServiceIMPL();
        List<GoodsRough> goodsRoughs=new ArrayList<>();
        for (OrderInfo orderInfo:orderInfos){
             int goodsDetailId=orderInfo.getGoodsDetailId();
             int goodsId=goodsDetailServiceIMPL.findGoodsId(goodsDetailId);
             GoodsRough goodsRough=goodsRoughServiceIMPL.findByGoodsRoughID(goodsId);
             goodsRoughs.add(goodsRough);

        }
        return goodsRoughs;
    }
   @GetMapping("/admin/findOrderNumberToday")
            public BigDecimal[] findOrderNumberToday(){

        return orderInfoServiceIMPL.findOrderNumberToday();
    }
    @GetMapping("/admin/findAllOrder")
    public List<OrderInfo> findAllOrder(){
       return orderInfoServiceIMPL.findAllOrder();
    }
    @GetMapping("/admin/findAllOrderDetail") //
    public List<GoodsRough>  findAllOrderDetail(){
        List<OrderInfo> orderInfos= orderInfoServiceIMPL.findAllOrder();


        List<GoodsRough> goodsRoughs=new ArrayList<>();
        for (OrderInfo orderInfo:orderInfos){
            int goodsDetailId=orderInfo.getGoodsDetailId();
            System.out.println(goodsDetailId);
            int goodsId=goodsDetailServiceIMPL.findGoodsId(goodsDetailId);
            GoodsRough goodsRough=goodsRoughServiceIMPL.findByGoodsRoughID(goodsId);
            goodsRoughs.add(goodsRough);

        }
        return goodsRoughs;
    }

    @GetMapping("/findOrderAndGoodsInfo")
    public List<OrderAndGoodsInfo> findOrderAndGoodsInfo(OrderInfo orderInfo)
    {
        System.out.println("---------------findOrderAndGoodsInfo-------------------------");
        System.out.println(orderInfo);
        System.out.println("-------------------------------------------------------------");
        System.out.println(orderInfoServiceIMPL.findOrderAndGoodsInfo(orderInfo));
       return orderInfoServiceIMPL.findOrderAndGoodsInfo(orderInfo);
    }
}
