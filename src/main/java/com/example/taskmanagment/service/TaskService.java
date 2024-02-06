package com.example.taskmanagment.service;

import com.example.taskmanagment.dto.task.Task;
import com.example.taskmanagment.entity.TaskEntity;
import com.example.taskmanagment.exeptions.IdNotFoundExeption;
import com.example.taskmanagment.exeptions.NameDuplicateExeption;
import javax.persistence.EntityNotFoundException;


public interface TaskService {
    TaskEntity createTask(Task task) throws NameDuplicateExeption;

    TaskEntity updateTask(Integer id,Task task) throws EntityNotFoundException, IdNotFoundExeption;

    void addComment(Integer taskId, String comments) throws IdNotFoundExeption;
}
