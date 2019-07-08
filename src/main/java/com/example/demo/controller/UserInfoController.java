package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoServiceIMPL userInfoServiceIMPL;


    @PostMapping("/deleteUserInfoByID/{id}") //checked
    public boolean deleteUserInfoByID(@PathVariable("id") String id){return userInfoServiceIMPL.deleteUserInfoByID(id);}

    @PostMapping("/updateUserInfo")  //checked
    public UserInfo updateUserInfo(UserInfo userInfo){return userInfoServiceIMPL.updateUserInfo(userInfo);}

    @GetMapping("/selectUserInfo/{id}")  //checked
    public UserInfo selectUserInfo(@PathVariable("id") String id){return userInfoServiceIMPL.selectUserInfo(id);}

    @GetMapping("/admin/findAllUserInfo")  //checked
	public List<UserInfo> findAllUserInfo(){
	 List<UserInfo> userInfos= userInfoServiceIMPL.findAllUserInfo();
        for (UserInfo o:userInfos
             ) {
            Date date=o.getEndTime();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//设置转化格式
            java.sql.Date date1= java.sql.Date.valueOf(sdf.format(date));
            o.setEndTime(date1);

        }
        return userInfos;}


    @PostMapping("/addUserInfo/{viptype}") //checked
    public boolean addUserInfo(UserInfo userInfo,@PathVariable("viptype") int viptype){return userInfoServiceIMPL.addUserInfo(userInfo,viptype);}

    //is vip = true / not = false
    @GetMapping("/isMember/{id}")
    public boolean isMember(@PathVariable("id") String id){return userInfoServiceIMPL.isMember(id);}

    @GetMapping("yourendtime/{id}")
    public Date yourEndDate(@PathVariable("id") String id){return userInfoServiceIMPL.yourEndDate(id);}
}
