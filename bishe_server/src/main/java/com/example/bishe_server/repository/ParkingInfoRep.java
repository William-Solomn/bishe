package com.example.bishe_server.repository;

import com.example.bishe_server.entity.ParkingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingInfoRep extends JpaRepository<ParkingInfoEntity,Long> {
    ParkingInfoEntity findParkingInfoEntityByParking_id(String parking_id);
}
