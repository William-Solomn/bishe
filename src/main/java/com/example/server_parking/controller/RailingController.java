package com.example.server_parking.controller;


import com.example.server_parking.mqtt.MQTTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}