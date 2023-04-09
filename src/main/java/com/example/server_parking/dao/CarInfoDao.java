package com.example.server_parking.dao;

import com.example.server_parking.entity.CarInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertCar(CarInfoEntity carInfo){
        jdbcTemplate.update("insert into car_info(car_id,car_number,date_came_in,date_leave_out,park_fee,is_pay,is_internal)values(?,?,?,?,?,?,?)",
                carInfo.getCar_id(),
                carInfo.getCar_number(),
                carInfo.getDate_came_in(),
                carInfo.getDate_leave_out(),
                carInfo.getPark_fee(),
                carInfo.isIs_pay(),
                carInfo.isIs_internal());
    }
    public List<CarInfoEntity> findAll() {
        String sql = "SELECT * FROM car_info";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CarInfoEntity.class));
    }
}
