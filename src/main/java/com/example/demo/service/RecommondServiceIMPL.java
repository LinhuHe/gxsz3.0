package com.example.demo.service;

import com.example.demo.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommondServiceIMPL {

    @Autowired
    private OrderInfoServiceIMPL orderInfoServiceIMPL;

    public List<OrderInfo> randomRecommandByOrderID(){

        return null;

    }

    public List<OrderInfo> brandRecommand(){

        return null;
    }

    public List<OrderInfo> newGoodsRecommand(){

        return null;
    }
}
