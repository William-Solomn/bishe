package com.example.server_parking.controller;

import com.example.server_parking.dao.CarInfoDao;
import com.example.server_parking.entity.CarInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("carinfo")
public class CarInfoController {

    // 一个接口，返回所有数据即可，且不可查看或改变
    Logger logger = LoggerFactory.getLogger(CarInfoController.class);
    @Autowired
    private CarInfoDao carInfoDao;

    // 返回表里所有数据
    @GetMapping("/getAllCarInfo")
    public List<CarInfoEntity> getAll() {
        List<CarInfoEntity> test = carInfoDao.findAll();
        logger.warn(String.valueOf(test));
        return test;
    }

//    // 一个接口，增加所有数据
//    @PostMapping("/addCarInfo")
//    public Boolean addCarInfo(@RequestParam("car_number")String car_number,
//                              @RequestParam("date_came_in") String date_came_in,
//                              @RequestParam("date_leave_out")String date_leave_out,
//                              @RequestParam("is_pay")int is_pay,
//                              @RequestParam("is_internal")int is_internal){
//        CarInfoEntity car = new CarInfoEntity();
//        car.setCar_id(UUID.randomUUID().toString());
//        car.setCar_number(car_number);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.warn("这个是date-came-in");
//        logger.warn(date_came_in);
//        date_came_in = date_came_in.replaceAll("[a-zA-Z]", " ");
//        logger.warn("这个替换后的date-came-in");
//        logger.warn(date_came_in);
//        car.setDate_came_in(Timestamp.valueOf(date_came_in));
//        logger.warn("这个是Timestamp date-came-in",Timestamp.valueOf(date_came_in));
//        car.setDate_leave_out(Timestamp.valueOf(date_came_in.replaceAll("[a-zA-Z]", " ")));
//        car.setIs_pay(is_pay);
//        car.setIs_internal(is_internal);
//        car.setPark_fee(25);
//        carInfoDao.insertCar(car);
//        return true;
//    }
    @CrossOrigin
    @PostMapping("/addCarInfo")
    @ResponseBody
    public Boolean addCarInfo(@RequestBody Map<String, String> params) {
        String car_number = params.get("car_number");
        String date_came_in = params.get("date_came_in");
        String date_leave_out = params.get("date_leave_out");
        int is_pay = Integer.parseInt(params.get("is_pay"));
        int is_internal = Integer.parseInt(params.get("is_internal"));

        CarInfoEntity car = new CarInfoEntity();
        car.setCar_id(UUID.randomUUID().toString());
        car.setCar_number(car_number);
        date_came_in = date_came_in.replaceAll("[a-zA-Z]", " ");
        car.setDate_came_in(Timestamp.valueOf(date_came_in));
        car.setDate_leave_out(Timestamp.valueOf(date_leave_out.replaceAll("[a-zA-Z]", " ")));
        car.setIs_pay(is_pay);
        car.setIs_internal(is_internal);
        car.setPark_fee(25);
        carInfoDao.insertCar(car);
        return true;
    }
}
