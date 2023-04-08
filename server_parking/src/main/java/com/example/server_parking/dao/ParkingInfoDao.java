package com.example.server_parking.dao;

import com.example.server_parking.entity.ParkingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ParkingInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateUseFul(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("update parking_info set parking_is_useful=? where parking_id=?",
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_id());
    }

    public void updateAll(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("update parking_info set parking_name=?, parking_position=?, parking_is_useful=?, parking_is_bought=?, parking_user_id=? where parking_id=?",
                parkingInfoEntity.getParking_name(),
                parkingInfoEntity.getParking_position(),
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_is_bought(),
                parkingInfoEntity.getParking_user_id(),
                parkingInfoEntity.getParking_id());
    }

    public void deleteOne(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("delete from parking_info where parking_id=?",
                parkingInfoEntity.getParking_id());
    }

    public void insertParking(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("insert into parking_info(parking_id,parking_name,parking_position,parking_is_useful,parking_is_bought,parking_user_id) values(?,?,?,?,?,?)",
                parkingInfoEntity.getParking_id(),
                parkingInfoEntity.getParking_name(),
                parkingInfoEntity.getParking_position(),
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_is_bought(),
                parkingInfoEntity.getParking_user_id());
    }

    public List<ParkingInfoEntity> findAll() {
        String sql = "SELECT * FROM parking_info";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ParkingInfoEntity.class));
    }
    public int findIsUseful(String parking_id) {
        String sql = "SELECT parking_is_useful from parking_info where parking_id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{parking_id}, Integer.class);
    }
    public int[] getParkingCounts() {
        String sql_can_use = "select sum(parking_is_useful=0) from parking_info";
        String sql_useing = "select sum(parking_is_useful=1) from parking_info";
        String sql_user_use = "select count(distinct parking_id) from parking_info where parking_is_useful=1 and parking_is_bought=1";

        int can_use_count = jdbcTemplate.queryForObject(sql_can_use, Integer.class);
        int using_count = jdbcTemplate.queryForObject(sql_useing, Integer.class);
        int user_use_count = jdbcTemplate.queryForObject(sql_user_use, Integer.class);

        int[] counts = new int[]{can_use_count, using_count, user_use_count};
        return counts;
    }

}
