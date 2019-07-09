package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserInfoExample;
import com.example.demo.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date ;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class UserInfoServiceIMPL  {
    @Autowired
    private UserInfoMapper userInfoMapper;

    public boolean deleteUserInfoByID(String id){
        boolean flag=false;
       UserInfoExample userInfoExample=new UserInfoExample();
        UserInfoExample.Criteria criteria=userInfoExample.createCriteria();

        criteria.andUserIdEqualTo(id);


        userInfoMapper.deleteByExample(userInfoExample);
        //userInfoMapper.selectByPrimaryKey(1);
        //userInfoMapper.deleteByPrimaryKey(1);
        System.out.println("删除完成");
        try{

            flag=true;
            return flag;
        }
        catch (Exception e)
        {
            return flag;
        }
    };
    public UserInfo updateUserInfo(UserInfo userInfo){

        try{
            String id=userInfo.getUserId();
            userInfoMapper.updateByPrimaryKey(userInfo);
            return userInfoMapper.selectByPrimaryKey(id);
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public UserInfo selectUserInfo(String id){
        return  userInfoMapper.selectByPrimaryKey(id);
    }

    public void updateLevel(UserInfo userInfo)
    {
        userInfoMapper.updateLevel(userInfo);
    }
    public List<UserInfo> findAllUserInfo(){
        UserInfoExample userInfoExample=new UserInfoExample();
        userInfoExample.or().andUserIdGreaterThan("");
        return userInfoMapper.selectByExample(userInfoExample);

    }


    public boolean addUserInfo(UserInfo userInfo,int viptype){
        boolean flag=false;
        boolean ismember = false;
        ismember = isMember(userInfo.getUserId());

        //not vip
        if(!ismember){
            //get nowtime add opentime
            Date nowdate = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(nowdate);
            calendar.add(Calendar.MONTH,viptype);//把日期往后增加一.正数往后推,负数往前推
            nowdate=calendar.getTime();
            System.out.println("not vip, viptype is:"+viptype+"  enddate is:"+nowdate);
            userInfo.setEndTime(nowdate);
            userInfo.setUserLevel(0);
            try{
                userInfoMapper.insert(userInfo);
                flag=true;
                return flag;
            }
            catch (Exception e)
            {
                return flag;
            }
        }
        else{ //is vip
            Date olddate = yourEndDate(userInfo.getUserId());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(olddate);
            calendar.add(Calendar.MONTH,viptype);//把日期往后增加.正数往后推,负数往前推
            Date newdate =calendar.getTime();
            System.out.println("is vip, viptype is:"+viptype+"   enddate is:"+newdate);
            userInfo.setEndTime(newdate);
            userInfoMapper.updateDate(userInfo);
            return true;
        }
    }

    public boolean isMember(String id)
    {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andUserIdEqualTo(id);
        long count = userInfoMapper.countByExample(userInfoExample);
        if(count==0) return false;
        else return true;
    }

    public Date yourEndDate(String id)
    {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.or().andUserIdEqualTo(id);

        Date date = new Date();
        date = userInfoMapper.selectByExample(userInfoExample).get(0).getEndTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd");
        String str = ft.format(date);
        System.out.println(str);
        try {
          date =  ft.parse(str);
        } catch (ParseException e) {
            System.out.println("not change time type");
            e.printStackTrace();
        }
        return date;
    }

    public void updatePhoneAndAddress(UserInfo userInfo)
    {
        userInfoMapper.updatePhoneAndAddress(userInfo);
    }

}
