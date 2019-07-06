package com.example.demo.mapper;

import com.example.demo.entity.GoodsRough;
import com.example.demo.entity.GoodsRoughExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsRoughMapper {
    long countByExample(GoodsRoughExample example);

    int deleteByExample(GoodsRoughExample example);

    int deleteByPrimaryKey(Integer goodsId);

    int insert(GoodsRough record);

    int insertSelective(GoodsRough record);

    List<GoodsRough> selectByExample(GoodsRoughExample example);

    GoodsRough selectByPrimaryKey(Integer goodsId);

    int updateByExampleSelective(@Param("record") GoodsRough record, @Param("example") GoodsRoughExample example);

    int updateByExample(@Param("record") GoodsRough record, @Param("example") GoodsRoughExample example);

    int updateByPrimaryKeySelective(GoodsRough record);

    int updateByPrimaryKey(GoodsRough record);

    List<GoodsRough> findDistinctBranch();

    List<GoodsRough> findTopFiveByDate();
}