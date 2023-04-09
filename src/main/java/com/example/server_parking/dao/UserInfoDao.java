package com.example.server_parking.dao;

import com.example.server_parking.entity.ParkingInfoEntity;
import com.example.server_parking.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void insertUser(UserInfoEntity userInfo){
        jdbcTemplate.update("insert into user_info(user_id,user_name,user_phone,user_car_number,remain_date) values(?,?,?,?,?)",
                userInfo.getUser_id(),
                userInfo.getUser_name(),
                userInfo.getUser_phone(),
                userInfo.getUser_car_number(),
                new Timestamp(userInfo.getRemain_date().getTime()));
    }
    public UserInfoEntity getUserByCarNumber(String user_car_number) {
        String sql = "select * from user_info where user_car_number=?";
        List<UserInfoEntity> list = jdbcTemplate.query(sql, new Object[]{user_car_number}, new BeanPropertyRowMapper<>(UserInfoEntity.class));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    public UserInfoEntity getUserById(String user_id) {
        String sql = "select * from user_info where user_id=?";
        List<UserInfoEntity> list = jdbcTemplate.query(sql, new Object[]{user_id}, new BeanPropertyRowMapper<>(UserInfoEntity.class));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    public UserInfoEntity getUserByName(String user_name) {
        String sql = "select * from user_info where user_name=?";
        List<UserInfoEntity> list = jdbcTemplate.query(sql, new Object[]{user_name}, new BeanPropertyRowMapper<>(UserInfoEntity.class));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<UserInfoEntity> findAll() {
        String sql = "SELECT * FROM user_info";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserInfoEntity.class));
    }
}
