package com.example.bishe_server.repository;

import com.example.bishe_server.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRep extends JpaRepository<UserInfoEntity, Long> {
    UserInfoEntity findUserInfoEntityByUser_id(String user_id);
}
