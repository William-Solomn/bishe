package com.example.bishe_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "CarInfo")
public class CarInfoEntity {
    @Id
    @Column(name = "car_id")
    private String car_id;

    @Column(name = "car_number")
    private String car_number;

    @Column(name = "date_came_in")
    private Timestamp date_came_in;

    @Column(name = "date_leave_out")
    private Timestamp date_leave_out;

    @Column(name = "park_fee")
    private int park_fee;

    @Column(name = "is_pay")
    private boolean is_pay;

    @Column(name = "is_internal")
    private boolean is_internal;

    public CarInfoEntity(String car_id, String car_number, Timestamp date_came_in, Timestamp date_leave_out, int park_fee, boolean is_pay, boolean is_internal) {
        this.car_id = car_id;
        this.car_number = car_number;
        this.date_came_in = date_came_in;
        this.date_leave_out = date_leave_out;
        this.park_fee = park_fee;
        this.is_pay = is_pay;
        this.is_internal = is_internal;
    }

    public CarInfoEntity() {

    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public Timestamp getDate_came_in() {
        return date_came_in;
    }

    public void setDate_came_in(Timestamp date_came_in) {
        this.date_came_in = date_came_in;
    }

    public Timestamp getDate_leave_out() {
        return date_leave_out;
    }

    public void setDate_leave_out(Timestamp date_leave_out) {
        this.date_leave_out = date_leave_out;
    }

    public int getPark_fee() {
        return park_fee;
    }

    public void setPark_fee(int park_fee) {
        this.park_fee = park_fee;
    }

    public boolean isIs_pay() {
        return is_pay;
    }

    public void setIs_pay(boolean is_pay) {
        this.is_pay = is_pay;
    }

    public boolean isIs_internal() {
        return is_internal;
    }

    public void setIs_internal(boolean is_internal) {
        this.is_internal = is_internal;
    }
}
