package com.example.taskmanagment.service.impl;


import com.example.taskmanagment.dto.task.Priority;
import com.example.taskmanagment.dto.task.StatusOfTask;
import com.example.taskmanagment.dto.task.Task;
import com.example.taskmanagment.entity.CommentsEntity;
import com.example.taskmanagment.entity.TaskEntity;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.exeptions.IdNotFoundExeption;
import com.example.taskmanagment.exeptions.NameDuplicateExeption;
import com.example.taskmanagment.mapper.TaskConvertor;
import com.example.taskmanagment.service.TaskService;
import com.example.taskmanagment.service.repository.TaskRepo;
import com.example.taskmanagment.service.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * a class for working with tasks that creates,
 * edits, deletes tasks, obtains a list of users,
 * assigns an executor, and validates incoming data
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final UserServiceImpl userService;
    private final TaskConvertor taskConvertor;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    /**
     *task creation, data validation, conversion to entity,
     * lookup username from userdetails of authenticated user
     * saving user in repository
     */
    @Transactional
    @Override
    public TaskEntity createTask(Task taskDto) {
        validateInput(taskDto);
        if (taskRepo.existsByName(taskDto.getName())) {
            throw new NameDuplicateExeption("name of task is used");
        }
        Task task = Task.builder()
                .description(taskDto.getDescription())
                .statusOfTask(taskDto.getStatusOfTask())
                .priority(taskDto.getPriority())
                .name(taskDto.getName())
                .build();
        TaskEntity entity = taskConvertor.convertToEntity(task);
        String user = userService.getUser();
        UserEntity userEntity = userRepo.findUserByEmail(user);
        entity.setUser(userEntity);
        taskRepo.save(entity);
        return entity;
    }
    /**
     *updating task by ID
     */
    @Transactional
    @Override
    public TaskEntity updateTask(Integer id, Task task) throws IdNotFoundExeption {
        logger.info("task data "+ task);
        TaskEntity taskEntity = taskRepo.findTaskEntityById(id);
        validateInput(task);
        logger.info("find task by id " + id);
        if (taskEntity != null) {
            taskEntity.setStatusOfTask(task.getStatusOfTask());
            taskEntity.setPriority(task.getPriority());
            taskEntity.setName(task.getName());
            taskEntity.setDescription(task.getDescription());
            logger.info("saving completed");
            return taskRepo.save(taskEntity);
        } else {
            throw new IdNotFoundExeption("id not found");
        }

    }
    /**
     * deleting a task by ID
     */
    public void deleteTask(Integer id) throws IdNotFoundExeption {
        TaskEntity task = taskRepo.findTaskEntityById(id);
        if (task != null) {
            taskRepo.delete(task);
            logger.info("task was deletet with id "+ id);
        } else {
            throw new IdNotFoundExeption("id not found");
        }
    }
    /**
     * appointment of executor
     */
    public void appointAnExecutor(String name, Integer taskId) {
        TaskEntity entity = taskRepo.findTaskEntityById(taskId);
        if (entity != null) {
            entity.setExecutor(name);
        }
    }
    /**
     * we get a list with the names of all performers
     */
    public List<TaskEntity> getAllTasks() {
        return taskRepo.findAll();
    }

    /**
     * search for a task by ID,
     * take the username from the security context
     * setting user data
     * creating a Comment Object
     * adding a comment to a task's comment lis
     * saving changes to the task repository
     */
    public void addComment(Integer taskId, String comment) throws IdNotFoundExeption {
        TaskEntity entity = taskRepo.findTaskEntityById(taskId);
        if (entity != null) {
            String user = userService.getUser();
            UserEntity userEntity = userRepo.findUserByEmail(user);
            userEntity.setEmail(user);
            entity.setId(taskId);
            CommentsEntity comments = new CommentsEntity();
            comments.setComment(comment);
            comments.setUser(userEntity);
            comments.setTask(entity);
            entity.getCommentsEntityList().add(comments);
            taskRepo.save(entity);
            logger.info("comment saved "+ comment);
        } else {
            throw new IdNotFoundExeption("id not found");
        }
    }
    /**
     * Validation of task input data.
     */
    private static void validateInput(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("task is null");
        }
        if (!task.getPriority().equals(Priority.valueOf(task.getPriority().name()))) {
            throw new IllegalArgumentException("select one of the options " + Priority.HIGH_PRIORITY
                    + Priority.MEDIUM_PRIORITY
                    + Priority.LOW_PRIORITY);
        }
        if (!task.getStatusOfTask().equals(StatusOfTask.valueOf(task.getStatusOfTask().name()))) {
            throw new IllegalArgumentException("select one of the options " + StatusOfTask.COMPLETE
                    + StatusOfTask.EXPECTATION
                    + StatusOfTask.IN_PROCESS);
        }
    }
}
