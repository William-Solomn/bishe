package com.example.server_parking.controller;

import com.example.server_parking.dao.UserInfoDao;
import com.example.server_parking.entity.ParkingInfoEntity;
import com.example.server_parking.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/userinfo")
public class UserInfoController {


    @Autowired
    private UserInfoDao userInfoDao;
    // 返回所有用户的信息
//    @RequestMapping("/getAllUser")
//    public List<UserInfoEntity> getAllUser(){
//        List<UserInfoEntity> list = new ArrayList<UserInfoEntity>();
//        list = userInfoRep.findAll();
//        return list;
//    }

    @GetMapping("/getAllUserInfo")
    public List<UserInfoEntity> getAll() {
        return userInfoDao.findAll();
    }

    // 根据post上来的车牌号，实现用户表查询，如果存在返回是，不存在返回否
    @PostMapping("/getUserByCarNumber")
    public Boolean getUserByCarNumber(@RequestParam("user_car_number") String user_car_number) {
        UserInfoEntity userInfo = userInfoDao.getUserByCarNumber(user_car_number);
        return userInfo != null;
    }


    // 新增用户，即ID是自己生成，其他的内容都是需要用户去填写
    @PostMapping("/addUser")
    public Boolean addUser(@RequestParam("user_name")String user_name
            ,@RequestParam("user_phone")String user_phone
            ,@RequestParam("user_car_number")String user_car_number
            ,@RequestParam("remain_date")String remain_date){
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUser_id(UUID.randomUUID().toString());
        userInfoEntity.setUser_name(user_name);
        userInfoEntity.setUser_phone(user_phone);
        userInfoEntity.setUser_car_number(user_car_number);
        userInfoEntity.setRemain_date(Timestamp.valueOf(remain_date));
        userInfoDao.insertUser(userInfoEntity);
        return true;
    }



}
