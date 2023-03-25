package com.example.server_parking.controller;

import com.example.server_parking.dao.CarInfoDao;
import com.example.server_parking.entity.CarInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("carinfo")
public class CarInfoController {

    // 一个接口，返回所有数据即可，且不可查看或改变

    @Autowired
    private CarInfoDao carInfoDao;

    // 返回表里所有数据
    @GetMapping("/getAllCarInfo")
    public List<CarInfoEntity> getAll() {
        return carInfoDao.findAll();
    }

    // 一个接口，增加所有数据
    @PostMapping("/addCarInfo")
    public Boolean addCarInfo(@RequestParam("car_number")String car_number,
                              @RequestParam("date_came_in") String date_came_in,
                              @RequestParam("date_leave_out")String date_leave_out,
                              @RequestParam("is_pay")int is_pay,
                              @RequestParam("is_internal")int is_internal){
        CarInfoEntity car = new CarInfoEntity();
        car.setCar_id(UUID.randomUUID().toString());
        car.setCar_number(car_number);
        car.setDate_came_in(Timestamp.valueOf(date_came_in));
        car.setDate_leave_out(Timestamp.valueOf(date_leave_out));
        car.setIs_pay(is_pay);
        car.setIs_internal(is_internal);
        car.setPark_fee(25);
        carInfoDao.insertCar(car);
        return true;
    }
}
