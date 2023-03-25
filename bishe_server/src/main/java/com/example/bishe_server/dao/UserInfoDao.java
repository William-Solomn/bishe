package com.example.bishe_server.dao;

import com.example.bishe_server.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class UserInfoDao {
    @Autowired
    private JdbcTemplate jt;
    public void insertUser(UserInfoEntity userInfo){
        jt.update("insert into UserInfo(user_id,user_name,user_phone,user_car_number,remain_date) values(?,?,?,?,?)",
                userInfo.getUser_id(),
                userInfo.getUser_name(),
                userInfo.getUser_phone(),
                userInfo.getUser_car_number(),
                new Timestamp(userInfo.getRemain_date().getTime()));
    }
    public UserInfoEntity getUserByCarNumber(String user_car_number) {
        String sql = "select * from UserInfo where user_car_number=?";
        List<UserInfoEntity> list = jt.query(sql, new Object[]{user_car_number}, new BeanPropertyRowMapper<>(UserInfoEntity.class));
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
