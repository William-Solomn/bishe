package com.example.bishe_server.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("test")
public class TestController {

//    try {
//        // 创建数据库连接
//        conn = DriverManager.getConnection(url, username, password);
//
//        // 创建 PreparedStatement 对象
//        String sql = "INSERT INTO mytable (mydatetime) VALUES (?)";
//        stmt = conn.prepareStatement(sql);
//
//        // 创建当前日期和时间对象
//        Date now = new Date();
//
//        // 将当前日期和时间对象转换为 java.sql.Timestamp 对象
//        Timestamp timestamp = new Timestamp(now.getTime());
//
//        // 设置 PreparedStatement 对象的参数
//        stmt.setTimestamp(1, timestamp);
//
//        // 执行 SQL 语句
//        stmt.executeUpdate();
//
//        System.out.println("数据插入成功！");
//    } catch (SQLException e) {
//        e.printStackTrace();
//    } finally {
//        // 关闭资源
//        try {
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (conn != null) {
//                conn.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
