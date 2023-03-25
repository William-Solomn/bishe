package com.example.bishe_server.repository;

import com.example.bishe_server.entity.CarInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarInfoRep extends JpaRepository<CarInfoEntity, Long> {
    CarInfoEntity findCarInfoEntityByCar_id(String car_id);
}
