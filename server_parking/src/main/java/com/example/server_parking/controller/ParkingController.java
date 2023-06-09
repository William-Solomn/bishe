package com.example.server_parking.controller;

import com.example.server_parking.dao.ParkingInfoDao;
import com.example.server_parking.entity.CarInfoEntity;
import com.example.server_parking.entity.ParkingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("parkinginfo")
public class ParkingController {

    @Autowired
    private ParkingInfoDao parkingInfoDao;
    private JdbcTemplate jdbcTemplate;

//    // 实现所有信息的返回
//    @RequestMapping("/getAllParkingInfo")
//    public List<ParkingInfoEntity> getAllParkingInfo(){
//        List<ParkingInfoEntity> list = new ArrayList<>();
//        list = parkingInfoRep.findAll();
//        return list;
//    }
// 返回表里所有数据
    @GetMapping("/getAllParking")
    public List<ParkingInfoEntity> getAll() {
        return parkingInfoDao.findAll();
    }

//    // 实现“是否可用”这个接口的修改
//    @PostMapping("/setIsUseful")
//    public Boolean setIsUseful(@RequestParam("parking_id")String parking_id){
//        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
//        parkingInfoEntity.setParking_id(parking_id);
//        int parking_is_useful = parkingInfoDao.findIsUseful(parking_id);
//        parkingInfoEntity.setParking_is_useful(parking_is_useful==1?0:1);
//        parkingInfoDao.updateUseFul(parkingInfoEntity);
//        return true;
//    }

// 如果距离距离小于2米，则就调用改接口，并设为不可用
    @CrossOrigin
    @PostMapping("/setIsUseful")
    @ResponseBody
    public Boolean setIsUseful(@RequestBody Map<String, String> params) {
        String parking_id = params.get("parking_id");

        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
        parkingInfoEntity.setParking_id(parking_id);
        int parking_is_useful = parkingInfoDao.findIsUseful(parking_id);
        parkingInfoEntity.setParking_is_useful(1);
        parkingInfoDao.updateUseFul(parkingInfoEntity);
        return true;
    }

    // 如果距离距离小于2米，则就调用改接口，并设为不可用
    @CrossOrigin
    @PostMapping("/setNotUseful")
    @ResponseBody
    public Boolean setNotUseful(@RequestBody Map<String, String> params) {
        String parking_id = params.get("parking_id");

        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
        parkingInfoEntity.setParking_id(parking_id);
        int parking_is_useful = parkingInfoDao.findIsUseful(parking_id);
        parkingInfoEntity.setParking_is_useful(0);
        parkingInfoDao.updateUseFul(parkingInfoEntity);
        return true;
    }

//    // 实现编辑按钮的操作，即修改某条数据的所有字段
//    @PostMapping("/updateParking")
//    public Boolean updateParking(@RequestParam("parking_id") String parking_id,
//                                 @RequestParam("parking_name") String parking_name,
//                                 @RequestParam("parking_position") String parking_position,
//                                 @RequestParam("parking_is_useful") int parking_is_useful,
//                                 @RequestParam("parking_is_bought") int parking_is_bought,
//                                 @RequestParam("parking_user_id") String parking_user_id) {
//        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
//        parkingInfoEntity.setParking_id(parking_id);
//        parkingInfoEntity.setParking_name(parking_name);
//        parkingInfoEntity.setParking_position(parking_position);
//        parkingInfoEntity.setParking_is_useful(parking_is_useful);
//        parkingInfoEntity.setParking_is_bought(parking_is_bought);
//        parkingInfoEntity.setParking_user_id(parking_user_id);
//
//        try {
//            parkingInfoDao.updateAll(parkingInfoEntity);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
@CrossOrigin
@PostMapping("/updateParking")
@ResponseBody
public Boolean updateParking(@RequestBody Map<String, String> params) {
    String parking_id = params.get("parking_id");
    String parking_name = params.get("parking_name");
    String parking_position = params.get("parking_position");
    int parking_is_useful = Objects.equals(params.get("parking_is_useful"), "true") ?1:0;
    int parking_is_bought = Objects.equals(params.get("parking_is_bought"), "true") ?1:0;
    String parking_user_id = params.get("parking_user_id");

    ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
    parkingInfoEntity.setParking_id(parking_id);
    parkingInfoEntity.setParking_name(parking_name);
    parkingInfoEntity.setParking_position(parking_position);
    parkingInfoEntity.setParking_is_useful(parking_is_useful);
    parkingInfoEntity.setParking_is_bought(parking_is_bought);
    parkingInfoEntity.setParking_user_id(parking_user_id);

    try {
        parkingInfoDao.updateAll(parkingInfoEntity);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}



//    // 删除操作，根据id删除某条数据
//    @PostMapping("/deleteParking")
//    public Boolean deleteParking(@RequestParam("parking_id")String parking_id){
//        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
//        parkingInfoEntity.setParking_id(parking_id);
//        parkingInfoDao.deleteOne(parkingInfoEntity);
//        return true;
//    }
    @CrossOrigin
    @PostMapping("/deleteParking")
    @ResponseBody
    public Boolean deleteParking(@RequestBody Map<String, String> params){
        String parking_id = params.get("parking_id");

        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
        parkingInfoEntity.setParking_id(parking_id);
        parkingInfoDao.deleteOne(parkingInfoEntity);
        return true;
    }
//    //新增一条数据
//    @PostMapping("/addParking")
//    public Boolean addParking(@RequestParam("parking_name") String parking_name,
//                              @RequestParam("parking_position") String parking_position,
//                              @RequestParam("parking_is_useful") int parking_is_useful,
//                              @RequestParam("parking_is_bought") int parking_is_bought,
//                              @RequestParam("parking_user_id") String parking_user_id) {
//        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
//
//        parkingInfoEntity.setParking_id(UUID.randomUUID().toString());
//        parkingInfoEntity.setParking_name(parking_name);
//        parkingInfoEntity.setParking_position(parking_position);
//        parkingInfoEntity.setParking_is_useful(parking_is_useful);
//        parkingInfoEntity.setParking_is_bought(parking_is_bought);
//        parkingInfoEntity.setParking_user_id(parking_user_id);
//
//        parkingInfoDao.insertParking(parkingInfoEntity);
//        return true;
//    }
    @CrossOrigin
    @PostMapping("/addParking")
    @ResponseBody
    public Boolean addParking(@RequestBody Map<String, Object> params) {
        String parking_name = (String) params.get("parking_name");
        String parking_position = (String) params.get("parking_position");
        int parking_is_useful = Objects.equals(params.get("parking_is_useful"), "true") ?1:0;
        int parking_is_bought = Objects.equals(params.get("parking_is_bought"), "true") ?1:0;
        String parking_user_id = (String) params.get("parking_user_id");

        ParkingInfoEntity parkingInfoEntity = new ParkingInfoEntity();
        parkingInfoEntity.setParking_id(UUID.randomUUID().toString());
        parkingInfoEntity.setParking_name(parking_name);
        parkingInfoEntity.setParking_position(parking_position);
        parkingInfoEntity.setParking_is_useful(parking_is_useful);
        parkingInfoEntity.setParking_is_bought(parking_is_bought);
        parkingInfoEntity.setParking_user_id(parking_user_id);
        parkingInfoDao.insertParking(parkingInfoEntity);
        return true;
    }
    @GetMapping("/getParkingCounts")
    public int[] getParkingCounts() {
        return parkingInfoDao.getParkingCounts();
    }


}
