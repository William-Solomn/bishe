package com.example.server_parking.dao;

import com.example.server_parking.entity.CarInfoEntity;
import com.example.server_parking.entity.ParkingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
