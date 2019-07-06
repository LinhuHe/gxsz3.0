package com.example.demo.mapper;

import com.example.demo.entity.UserLogin;
import com.example.demo.entity.UserLoginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLoginMapper {
    long countByExample(UserLoginExample example);

    int deleteByExample(UserLoginExample example);

    int deleteByPrimaryKey(String userId);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    List<UserLogin> selectByExample(UserLoginExample example);

    UserLogin selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") UserLogin record, @Param("example") UserLoginExample example);

    int updateByExample(@Param("record") UserLogin record, @Param("example") UserLoginExample example);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);
}