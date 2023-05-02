package com.example.server_parking.controller;


import com.example.server_parking.dao.ParkingInfoDao;
import com.example.server_parking.mqtt.MQTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("railing")
public class RailingController {

    @Autowired
    private MQTTService mqttService;

    @PostMapping("/liftGate")
    public ResponseEntity<Void> liftGate() {
        mqttService.sendLiftGateCommand();
        return ResponseEntity.ok().build();
    }

    @Autowired
    private ParkingInfoDao parkingInfoDao;

    @PostMapping("/distance")
    public ResponseEntity<Void> handleDistance(@RequestBody Map<String, String> params) {
        String distanceStr = params.get("distance");
        double distance = Double.parseDouble(distanceStr);

        int parking_is_useful = distance < 2 ? 1 : 0;
        mqttService.setParkingIsUseful(parking_is_useful);

        return ResponseEntity.ok().build();
    }

}