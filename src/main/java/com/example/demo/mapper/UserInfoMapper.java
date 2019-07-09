package com.example.demo.mapper;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import javax.jws.soap.SOAPBinding;

public interface UserInfoMapper {
    long countByExample(UserInfoExample example);

    int deleteByExample(UserInfoExample example);

    int deleteByPrimaryKey(String userId);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    List<UserInfo> selectByExample(UserInfoExample example);

    UserInfo selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByExample(@Param("record") UserInfo record, @Param("example") UserInfoExample example);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    void updateDate(UserInfo userInfo);

    void  updateLevel(UserInfo userInfo);

    void updatePhoneAndAddress(UserInfo userInfo);
}