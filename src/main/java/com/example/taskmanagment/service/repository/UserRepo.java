package com.example.taskmanagment.service.repository;

import com.example.taskmanagment.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserById (Integer id);

    UserEntity findUserByEmail(String email);




}
