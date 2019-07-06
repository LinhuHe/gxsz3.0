package com.example.demo.mapper;

import com.example.demo.entity.GoodsDetail;
import com.example.demo.entity.GoodsDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsDetailMapper {
    long countByExample(GoodsDetailExample example);

    int deleteByExample(GoodsDetailExample example);

    int deleteByPrimaryKey(Integer goodsDetaiId);

    int insert(GoodsDetail record);

    int insertSelective(GoodsDetail record);

    List<GoodsDetail> selectByExample(GoodsDetailExample example);

    GoodsDetail selectByPrimaryKey(Integer goodsDetaiId);

    int updateByExampleSelective(@Param("record") GoodsDetail record, @Param("example") GoodsDetailExample example);

    int updateByExample(@Param("record") GoodsDetail record, @Param("example") GoodsDetailExample example);

    int updateByPrimaryKeySelective(GoodsDetail record);

    int updateByPrimaryKey(GoodsDetail record);
}