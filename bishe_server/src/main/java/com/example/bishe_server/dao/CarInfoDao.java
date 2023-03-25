package com.example.bishe_server.dao;

import com.example.bishe_server.entity.CarInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertCar(CarInfoEntity carInfo){
        jdbcTemplate.update("insert into CarInfo(car_id,car_number,date_came_in,date_leave_out,park_fee,is_pay,is_internal)",
                carInfo.getCar_id(),
                carInfo.getCar_number(),
                carInfo.getDate_came_in(),
                carInfo.getDate_leave_out(),
                carInfo.getPark_fee(),
                carInfo.isIs_pay(),
                carInfo.isIs_internal());
    }
}
