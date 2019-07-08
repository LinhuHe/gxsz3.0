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
    @Autowired
    private UserInfoServiceIMPL userInfoServiceIMPL;
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



            //检测是否有这个id
            if (orderInfo.getUserId() == null)
            {
                System.out.println("hewe");
                return false;
            }
            Date endday = userInfoServiceIMPL.selectUserInfo(orderInfo.getUserId()).getEndTime();
            //查看会员是否到期
            Date now = new Date();
            if (endday.after(now)) {


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
                //更新积分
                UserInfo userInfo = userInfoServiceIMPL.selectUserInfo(orderInfo.getUserId());
                userInfo.setUserLevel(level+orderInfo.getOrderPrice().intValue());
                userInfoServiceIMPL.updateLevel(userInfo);

                double price = orderInfo.getOrderPrice().doubleValue() * discount;
                orderInfo.setOrderPrice(BigDecimal.valueOf(price));

                /*//删除购物车里的同id商品
                ShopCart shopCart = new ShopCart();
                shopCart.setUserId(orderInfo.getUserId());
                shopCart.setGoodsDetailId(orderInfo.getGoodsDetailId());

                ShopCartServiceIMPL shopCartServiceIMPL = new ShopCartServiceIMPL();
                shopCartServiceIMPL.deleteShopCardByBothID(shopCart);*/


                //添加订单
                orderInfoMapper.insert(orderInfo);

                //库存减少
                int newstock = rs.get(0).getStock()-orderInfo.getAmount();
                goodsDetail.setStock(newstock);
                goodsDetailServiceIMPL.updateStock(goodsDetail);

            }
            else{
                System.out.println("you are not vip anymore");
                return false;
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
    public OrderInfo buildOrder(ShopCart shopCart,GoodsDetail mygoodsDetail,int days,String dilivery){
        //获取现在的日期
        OrderInfo bulidorder = new OrderInfo();

        Calendar now=Calendar.getInstance();
        Date nowDate=now.getTime();
        //计算归还日期
        Calendar leaseTerm=Calendar.getInstance();
        leaseTerm.add(Calendar.DATE,days);
        Date leaseTermDate=leaseTerm.getTime();

        bulidorder.setOrderDate(nowDate);
        bulidorder.setLeaseTerm(leaseTermDate);

        //set dilivery
        bulidorder.setDelivery(dilivery);

        //get price
        BigDecimal price = goodsRoughServiceIMPL.findByGoodsRoughID(shopCart.getGoodsDetailId()).getGoodsPrice();
        BigDecimal amount = new BigDecimal(Integer.toString(shopCart.getAmount()));
        bulidorder.setOrderPrice(price.multiply(amount));

        //set amount
        bulidorder.setAmount(shopCart.getAmount());

        //set userid
        bulidorder.setUserId(shopCart.getUserId());

        //get did
        int did = goodsDetailServiceIMPL.findWhateverYouWant(mygoodsDetail).get(0).getGoodsDetaiId();
        bulidorder.setGoodsDetailId(did);
        bulidorder.setStatus(0);

        //is sold out?
        if(goodsDetailServiceIMPL.findWhateverYouWant(mygoodsDetail).get(0).getStock()>0)
            return bulidorder;
        else{
            System.out.println("this is sold out");
            return null;
        }

        /*//得到单价
        GoodsDetail goodsDetail=new GoodsDetail();
        goodsDetail.setGoodsId(shopCart.getGoodsDetailId()); //shopcart中did其实是rid
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

        orderInfo.setStatus(0);*/

    }

    //找到今日的订单总数/金额，总交易额
    public BigDecimal[] findOrderNumberToday(){
        Date now=new Date();
        Date start=new Date();

        Calendar yestoday=Calendar.getInstance();
        yestoday.add(Calendar.HOUR_OF_DAY,-24);

        Calendar today=Calendar.getInstance();

        Date todayDate=today.getTime();
        Date yestodayDate=yestoday.getTime();
        //获取今日订单
        List<OrderInfo> rs=this.findOrderBetween(yestodayDate,todayDate);

        BigDecimal info[]=new BigDecimal[3];
        //TODO
        //类型转换并装入

        double count=rs.size();
        BigDecimal tmp=BigDecimal.valueOf(count);
        info[0]=tmp;

        BigDecimal money=BigDecimal.valueOf(0);
        OrderInfo orderInfo=new OrderInfo();

        //遍历获取今日交易额
        for (int i=0;i<count;i++){
            orderInfo=rs.get(i);
            money=money.add(orderInfo.getOrderPrice());
        }

	info[1]=money;



        List<OrderInfo> allOrder=this.findAllOrder();
        BigDecimal all=BigDecimal.valueOf(0);
        int p=allOrder.size();

        for (int i=0;i<p;i++){
            all=all.add(allOrder.get(i).getOrderPrice());
        }

        info[2]=all;

        return info;


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

	public List<OrderInfo> findAllOrder(){
        OrderInfoExample orderInfoExample=new OrderInfoExample();
        OrderInfoExample.Criteria criteria=orderInfoExample.createCriteria();

        criteria.andOrderIdGreaterThan(0);

        return orderInfoMapper.selectByExample(orderInfoExample);
    }
}
