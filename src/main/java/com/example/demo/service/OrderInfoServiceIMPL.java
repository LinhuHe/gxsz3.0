package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//question:可能不应该声明private...
@Service
public class OrderInfoServiceIMPL {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private GoodsDetailServiceIMPL goodsDetailServiceIMPL;
    @Autowired
    private GoodsRoughServiceIMPL goodsRoughServiceIMPL;
    //直接更新orderInfo
    public void updateOrderInfo(OrderInfo orderInfo){
        orderInfoMapper.updateByPrimaryKey(orderInfo);
    }
    //直接删除
    public void deleteOrderInfo(Integer id){
        orderInfoMapper.deleteByPrimaryKey(id);
    }
    //下订单：计算会员优惠--删除购物车里的订单-写入数据库 -------true为成功，false为没有库存或出错
    public boolean addOrderInfo(OrderInfo orderInfo){
        System.out.println(orderInfo);
        try {
            //检测是否有库存和商品是否存在
            GoodsDetail goodsDetail = new GoodsDetail();
            goodsDetail.setGoodsDetaiId(orderInfo.getGoodsDetailId());
            System.out.println(goodsDetail);
            GoodsDetailServiceIMPL goodsDetailServiceIMPL = new GoodsDetailServiceIMPL();
            List<GoodsDetail> rs = goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail);
            if ((rs == null) || (rs.isEmpty()))
            {
                System.out.println("No result");
                return false;
            }

            GoodsDetail tmp = rs.get(0);
            if (tmp.getStock() <= 0) {
                System.out.println("No stock");
                return false;
            }


            UserInfoServiceIMPL userInfoServiceIMPL = new UserInfoServiceIMPL();
            //检测是否有这个id
            if (userInfoServiceIMPL.selectUserInfo(orderInfo.getUserId()).getUserId() != null)
            {
                System.out.println("hewe");
                return false;
            }
            Date endday = userInfoServiceIMPL.selectUserInfo(orderInfo.getUserId()).getEndTime();
            //查看会员是否到期
            Date now = new Date();
            if (endday.before(now)) {


                //计算会员优惠--- 1级0-200 9折，2级200-400 8折，3级400+
                int level = userInfoServiceIMPL.selectUserInfo(orderInfo.getUserId()).getUserLevel();
                double discount = 1;
                if (level < 200) {
                    discount = 0.9;
                } else if (level < 400) {
                    discount = 0.8;
                } else {
                    discount = 0.7;
                }
                double price = orderInfo.getOrderPrice().doubleValue();
                orderInfo.setOrderPrice(BigDecimal.valueOf(price));
                //更新积分--不做了

                //订单创建时间和预计归还时间--不用做


                //删除购物车里的同id商品
                ShopCart shopCart = new ShopCart();
                shopCart.setUserId(orderInfo.getUserId());
                shopCart.setGoodsDetailId(orderInfo.getGoodsDetailId());

                ShopCartServiceIMPL shopCartServiceIMPL = new ShopCartServiceIMPL();
                shopCartServiceIMPL.deleteShopCardByBothID(shopCart);
                ;

                //添加订单
                orderInfoMapper.insert(orderInfo);

            }


        }
        catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    //按用户id查找订单
    public List<OrderInfo> findOrderByUserID(String id){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        orderInfoExample.or().andUserIdEqualTo(id);
        return orderInfoMapper.selectByExample(orderInfoExample);
    }

    //按照gooddetailid查找
    public List<OrderInfo> findOrderByGoodsDetailID(Integer id){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        orderInfoExample.or().andGoodsDetailIdEqualTo(id);
        return orderInfoMapper.selectByExample(orderInfoExample);

    }

    //按照orderid查找
    public List<OrderInfo> findOrderByOrderId(Integer id){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        orderInfoExample.or().andOrderIdEqualTo(id);
        return orderInfoMapper.selectByExample(orderInfoExample);
    }

    //任意查询OrderInfo
    public List<OrderInfo> findWhateverYouWant(OrderInfo orderInfo){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();
        //可通过userId,OrderId,GoodsDetailId,Status来搜索
        if(orderInfo.getUserId()!=null){
            criteria.andUserIdEqualTo(orderInfo.getUserId());
        }
        if(orderInfo.getOrderId()!=null){
            criteria.andOrderIdEqualTo(orderInfo.getOrderId());
        }
        if (orderInfo.getGoodsDetailId()!=null){
            criteria.andGoodsDetailIdEqualTo(orderInfo.getGoodsDetailId());
        }
        if (orderInfo.getStatus()!=null){
            criteria.andStatusEqualTo(orderInfo.getStatus());
        }


        return orderInfoMapper.selectByExample(orderInfoExample);
    }

    //通过userid,goodsdetailid,租期，快递构建一个构建一个order
    public OrderInfo buildOrder(ShopCart shopCart,int days,String dilivery){
        //获取现在的日期
        Calendar now=Calendar.getInstance();
        Date nowDate=now.getTime();
        //计算归还日期
        Calendar leaseTerm=Calendar.getInstance();
        leaseTerm.add(Calendar.DATE,days);
        Date leaseTermDate=leaseTerm.getTime();

        //得到单价
        GoodsDetail goodsDetail=new GoodsDetail();
        goodsDetail.setGoodsId(shopCart.getGoodsDetailId());
        List<GoodsDetail> rsList=goodsDetailServiceIMPL.findWhateverYouWant(goodsDetail);
        if(rsList.isEmpty())
        {
            return null;
        }
        GoodsDetail selectResult=rsList.get(0);

        GoodsRough goodsRough=new GoodsRough();

        goodsRough=goodsRoughServiceIMPL.findByGoodsRoughID(selectResult.getGoodsId());

        OrderInfo orderInfo=new OrderInfo();

        //构建order
        orderInfo.setUserId(shopCart.getUserId());
        orderInfo.setGoodsDetailId(shopCart.getGoodsDetailId());
        orderInfo.setAmount(shopCart.getAmount());

        //计算订单价格
        BigDecimal amountBigdecimal=new BigDecimal(Integer.toString(orderInfo.getAmount()));
        BigDecimal orderPrice=goodsRough.getGoodsPrice().multiply(amountBigdecimal);

        orderInfo.setOrderPrice(orderPrice);
        orderInfo.setDelivery(dilivery);
        orderInfo.setOrderDate(nowDate);
        orderInfo.setLeaseTerm(leaseTermDate);

        //TOdo
        //确定订单状态

        orderInfo.setStatus(0);

        return orderInfo;
    }

    //找到今日的订单总数/金额，总交易额
    public void findOrderNumberToday(){
        Date now=new Date();
        Date start=new Date();

        Calendar yestoday=Calendar.getInstance();
        yestoday.add(Calendar.HOUR_OF_DAY,-24);

        Calendar today=Calendar.getInstance();

        Date todayDate=today.getTime();
        Date yestodayDate=yestoday.getTime();
        //获取今日订单
        List<OrderInfo> rs=this.findOrderBetween(yestodayDate,todayDate);

        BigDecimal info[]=new BigDecimal[2];
        //TODO
        //类型转换并装入

        int count=rs.size();

        BigDecimal money=new BigDecimal(0);
        OrderInfo orderInfo=new OrderInfo();

        //遍历获取今日交易额
        for (int i=0;i<count;i++){
            orderInfo=rs.get(i);
            money.add(orderInfo.getOrderPrice());
        }




    }

    //找到2个时间之间的的订单
    public List<OrderInfo> findOrderBetween(Date start,Date end){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();

        criteria.andOrderDateBetween(start,end);

        return orderInfoMapper.selectByExample(orderInfoExample);

    }
    public List<OrderInfo> findOrderByStatus(Integer status){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        orderInfoExample.or().andStatusEqualTo(status);
        return orderInfoMapper.selectByExample(orderInfoExample);
    }

}
