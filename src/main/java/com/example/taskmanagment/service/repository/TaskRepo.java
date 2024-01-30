package com.example.taskmanagment.service.repository;

import com.example.taskmanagment.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepo extends JpaRepository<TaskEntity,Integer> {


    TaskEntity findTaskEntityById(Integer id);

    TaskEntity findTaskByName(String name);

    boolean existsByName(String name);


}
