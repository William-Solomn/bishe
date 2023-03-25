package com.example.bishe_server.controller;

import com.example.bishe_server.dao.CarInfoDao;
import com.example.bishe_server.entity.CarInfoEntity;
import com.example.bishe_server.repository.CarInfoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("carinfo")
public class CarInfoController {

    // 一个接口，返回所有数据即可，且不可查看或改变
    @Autowired
    private CarInfoRep carInfoRep;
    @Autowired
    private CarInfoDao carInfoDao;

    @RequestMapping("/getAllCarInfo")
    public List<CarInfoEntity> getAllCarInfo(@RequestParam("car_id") String car_id){
//        CarInfoEntity carInfoEntity = new CarInfoEntity();
//        carInfoEntity = carInfoRep.findCarInfoEntityByCar_id(car_id);
        List<CarInfoEntity> list = new ArrayList<CarInfoEntity>();
        list = carInfoRep.findAll();
        return list;
    }


    // 一个接口，增加所有数据
    @PostMapping("/addCarInfo")
    public Boolean addCarInfo(@RequestParam("car_id")String car_id,
                              @RequestParam("car_number")String car_number,
                              @RequestParam("date_came_in") String date_came_in,
                              @RequestParam("date_leave_out")String date_leave_out,
                              @RequestParam("is_pay")int is_pay,
                              @RequestParam("is_internal")int is_internal){
        CarInfoEntity car = new CarInfoEntity();
        car.setCar_id(car_id);
        car.setCar_number(car_number);
        car.setDate_came_in(Timestamp.valueOf(date_came_in));
        car.setDate_leave_out(Timestamp.valueOf(date_leave_out));
        car.setIs_pay(is_pay);
        car.setIs_internal(is_internal);
        carInfoDao.insertCar(car);
        return true;
    }
}
