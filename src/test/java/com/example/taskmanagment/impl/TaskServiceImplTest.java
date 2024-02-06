package com.example.taskmanagment.impl;

import com.example.taskmanagment.dto.task.Task;
import com.example.taskmanagment.dto.user.User;
import com.example.taskmanagment.entity.TaskEntity;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.exeptions.IdNotFoundExeption;
import com.example.taskmanagment.mapper.TaskConvertor;
import com.example.taskmanagment.service.impl.TaskServiceImpl;
import com.example.taskmanagment.service.impl.UserServiceImpl;
import com.example.taskmanagment.service.repository.TaskRepo;
import com.example.taskmanagment.service.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import static com.example.taskmanagment.dto.task.Priority.LOW_PRIORITY;
import static com.example.taskmanagment.dto.task.StatusOfTask.EXPECTATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "testName")
class TaskServiceImplTest {
    @Mock
    TaskRepo taskRepo;
    @Mock
    UserRepo userRepo;
    @Mock
    TaskConvertor convertor;

    @InjectMocks
    TaskServiceImpl taskService;
    @Mock
    UserServiceImpl userService;
    Task task = Task.builder()
            .statusOfTask(EXPECTATION)
            .priority(LOW_PRIORITY)
            .name("настроить логгирование")
            .description("просмотреть все ветки проекта и настроить логгирование где необходимо")
            .build();
   User user = User.builder()
           .email("boboja@mail.ru")
           .password("testPasswod")
           .firstName("Alex")
           .lastName("Kha")
           .build();
    @Test
    void createTask() {
        TaskEntity taskEntity = new TaskEntity();
        UserEntity userEntity = new UserEntity();

        when(userService.getUser()).thenReturn(user.getEmail());
        when(userRepo.findUserByEmail(anyString())).thenReturn(userEntity);
        when(convertor.convertToEntity(any(Task.class))).thenReturn(taskEntity);
        when(taskRepo.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskEntity entity = taskService.createTask(task);
        taskEntity.setUser(userEntity);
        entity.setName(task.getName());
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(task.getName(), entity.getName());

        verify(convertor, times(1)).convertToEntity(task);
        verify(taskRepo, times(1)).save(entity);

    }

    @Test
    void updateTask() throws IdNotFoundExeption {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1);
        task.setDescription("dis");
        task.setName("name");
        when(taskRepo.findTaskEntityById(taskEntity.getId())).thenReturn(taskEntity);
        when(taskRepo.save(any(TaskEntity.class))).thenReturn(taskEntity);
        TaskEntity entity = taskRepo.findTaskEntityById(taskEntity.getId());
        TaskEntity updateTask = taskService.updateTask(taskEntity.getId(), task);
        Assertions.assertNotNull(entity);
        Assertions.assertNotNull(updateTask);
        Assertions.assertEquals("dis",taskEntity.getDescription());
        Assertions.assertEquals("name",taskEntity.getName());

        verify(taskRepo, times(1)).save(entity);
        verify(taskRepo, times(2)).findTaskEntityById(taskEntity.getId());

    }

    @Test
    void addComment() {
    }
}