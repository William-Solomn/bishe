package com.example.bishe_server.dao;

import com.example.bishe_server.entity.ParkingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ParkingInfoDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateUseFul(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("update ParkingInfo set parking_is_useful=? where parking_id=?",
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_id());
    }

    public void updateAll(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("update ParkingInfo set parking_name=?, parking_position=?, parking_is_useful=?, parking_is_bought=?, parking_user_id=? where parking_id=?",
                parkingInfoEntity.getParking_name(),
                parkingInfoEntity.getParking_position(),
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_is_bought(),
                parkingInfoEntity.getParking_user_id(),
                parkingInfoEntity.getParking_id());
    }

    public void deleteOne(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("delete from ParkingInfo where parking_id=?",
                parkingInfoEntity.getParking_id());
    }

    public void insertParking(ParkingInfoEntity parkingInfoEntity){
        jdbcTemplate.update("insert into ParkingInfo(parking_id,parking_name,parking_position,parking_is_useful,parking_is_bought,parking_user_id) values(?,?,?,?,?,?)",
                parkingInfoEntity.getParking_id(),
                parkingInfoEntity.getParking_name(),
                parkingInfoEntity.getParking_position(),
                parkingInfoEntity.getParking_is_useful(),
                parkingInfoEntity.getParking_is_bought(),
                parkingInfoEntity.getParking_user_id());
    }
}
