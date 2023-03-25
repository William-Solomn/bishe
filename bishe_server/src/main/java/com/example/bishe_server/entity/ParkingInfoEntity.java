package com.example.bishe_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "ParkingInfo")
public class ParkingInfoEntity {
    @Id
    @Column(name = "parking_id")
    private String parking_id;

    @Column(name = "parking_name")
    private String parking_name;

    @Column(name = "parking_position")
    private String parking_position;

    @Column(name = "parking_is_useful")
    private int parking_is_useful;

    @Column(name = "parking_is_bought")
    private int parking_is_bought;

    @Column(name = "parking_user_id")
    private String parking_user_id;

    public ParkingInfoEntity() {

    }

    public String getParking_id() {
        return parking_id;
    }

    public void setParking_id(String parking_id) {
        this.parking_id = parking_id;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_position() {
        return parking_position;
    }

    public void setParking_position(String parking_position) {
        this.parking_position = parking_position;
    }

    public int getParking_is_useful() {
        return parking_is_useful;
    }

    public void setParking_is_useful(int parking_is_useful) {
        this.parking_is_useful = parking_is_useful;
    }

    public int getParking_is_bought() {
        return parking_is_bought;
    }

    public void setParking_is_bought(int parking_is_bought) {
        this.parking_is_bought = parking_is_bought;
    }

    public String getParking_user_id() {
        return parking_user_id;
    }

    public void setParking_user_id(String parking_user_id) {
        this.parking_user_id = parking_user_id;
    }

    public ParkingInfoEntity(String parking_id, String parking_name, String parking_position, int parking_is_useful, int parking_is_bought, String parking_user_id) {
        this.parking_id = parking_id;
        this.parking_name = parking_name;
        this.parking_position = parking_position;
        this.parking_is_useful = parking_is_useful;
        this.parking_is_bought = parking_is_bought;
        this.parking_user_id = parking_user_id;
    }
}
