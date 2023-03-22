package com.example.bishe_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserInfo")
public class UserInfoEntity {
    @Id
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "user_phone")
    private String user_phone;

    @Column(name = "user_car_number")
    private String user_car_number;

    @Column(name = "remain_date")
    private int remain_date;

    public UserInfoEntity(String user_id, String user_name, String user_phone, String user_car_number, int remain_date) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_car_number = user_car_number;
        this.remain_date = remain_date;
    }

    public UserInfoEntity() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_car_number() {
        return user_car_number;
    }

    public void setUser_car_number(String user_car_number) {
        this.user_car_number = user_car_number;
    }

    public int getRemain_date() {
        return remain_date;
    }

    public void setRemain_date(int remain_date) {
        this.remain_date = remain_date;
    }
}
