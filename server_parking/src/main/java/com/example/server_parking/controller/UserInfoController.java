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
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("userinfo")
public class UserInfoController {


    @Autowired
    private UserInfoDao userInfoDao;

    @GetMapping("/getAllUserInfo")
    public List<UserInfoEntity> getAll() {
        return userInfoDao.findAll();
    }

//    // 根据post上来的车牌号，实现用户表查询，如果存在返回是，不存在返回否
//    @PostMapping("/getUserByCarNumber")
//    public Boolean getUserByCarNumber(@RequestParam("user_car_number") String user_car_number) {
//        UserInfoEntity userInfo = userInfoDao.getUserByCarNumber(user_car_number);
//        return userInfo != null;
//    }
    @CrossOrigin
    @PostMapping("/getUserByCarNumber")
    @ResponseBody
    public Boolean getUserByCarNumber(@RequestBody Map<String, String> params) {
        String user_car_number = params.get("user_car_number");
        UserInfoEntity userInfo = userInfoDao.getUserByCarNumber(user_car_number);
        return userInfo != null;
    }
//    @PostMapping("/getUserById")
//    public UserInfoEntity getUserById(@RequestParam("user_id") String user_id) {
//        UserInfoEntity userInfo = userInfoDao.getUserById(user_id);
//        return userInfo;
//    }
    @CrossOrigin
    @PostMapping("/getUserById")
    @ResponseBody
    public UserInfoEntity getUserById(@RequestBody Map<String, String> params) {
        String user_id = params.get("user_id");
        UserInfoEntity userInfo = userInfoDao.getUserById(user_id);
        return userInfo;
    }
//    @PostMapping("/getUserByName")
//    public UserInfoEntity getUserByName(@RequestParam("user_name") String user_name) {
//        UserInfoEntity userInfo = userInfoDao.getUserByName(user_name);
//        return userInfo;
//    }
    @CrossOrigin
    @PostMapping("/getUserByName")
    @ResponseBody
    public UserInfoEntity getUserByName(@RequestBody Map<String, String> params) {
        String user_name = params.get("user_name");
        UserInfoEntity userInfo = userInfoDao.getUserByName(user_name);
        return userInfo;
    }

    @CrossOrigin
    @PostMapping("/addUser")
    @ResponseBody
    public Boolean addUser(@RequestBody Map<String, String> params){
        String user_name = params.get("user_name");
        String user_phone = params.get("user_phone");
        String user_car_number = params.get("user_car_number");
        String remain_date = params.get("remain_date");

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUser_id(UUID.randomUUID().toString());
        userInfoEntity.setUser_name(user_name);
        userInfoEntity.setUser_phone(user_phone);
        userInfoEntity.setUser_car_number(user_car_number);
        remain_date = remain_date.replaceAll("[a-zA-Z]", " ");
        userInfoEntity.setRemain_date(Timestamp.valueOf(remain_date));
        userInfoDao.insertUser(userInfoEntity);
        return true;
    }




}
