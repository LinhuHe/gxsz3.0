package com.example.demo.service;

import com.example.demo.entity.UserLogin;
import com.example.demo.entity.UserLoginExample;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.mapper.UserLoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServiceIMPL  {
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserInfoServiceIMPL userInfoServiceIMPL;


  //返回值为FLASE为假，true为真

    public Boolean login(UserLogin user){
        userLoginMapper.selectByPrimaryKey(user.getUserId());
        Boolean flag=false;
        UserLogin userLogin=null;
       userLogin=userLoginMapper.selectByPrimaryKey(user.getUserId());
            if (userLogin!=null)
            {
                if(userLogin.getUserPassword().equals(user.getUserPassword()))
                {flag=true;}
            }
        return flag;
    };


    public Boolean regis(UserLogin user){
        boolean flag=false;
        if(user==null)
        {
            return flag;
        }else if(user.getUserPassword()==null)
        {
            return flag;
        }
        try{userLoginMapper.insert(user);
            flag=true;
            return flag;
        }
        catch (Exception e){
            return flag;
        }
    };

    public Boolean reset_password(String password){
        boolean flag=false;
        return false;
    };


    public List<UserLogin> findAllUserLogin(){
        UserLoginExample userLoginExample =new UserLoginExample();
        userLoginExample.or().andUserIdGreaterThan("");
        return userLoginMapper.selectByExample(userLoginExample);

    }

    public boolean addUserLogin(UserLogin userLogin){
        if(userInfoServiceIMPL.isMember(userLogin.getUserId())) return false;
        else {
            userLoginMapper.insert(userLogin);
            return true;
        }
    }

}
