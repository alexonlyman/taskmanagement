package com.example.taskmanagment.impl;

import com.example.taskmanagment.dto.user.UpdateUser;
import com.example.taskmanagment.dto.user.User;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.service.impl.UserServiceImpl;
import com.example.taskmanagment.service.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepo userRepo;
    @InjectMocks
    UserServiceImpl userService;
    UpdateUser updateUser = new UpdateUser("Alex", "Kha", "boboja@mail.ru");
    UserEntity entity = new UserEntity();
    User user = User.builder()
            .email("boboja@mail.ru")
            .password("testPasswod")
            .firstName("Alex")
            .lastName("Kha")
            .build();


    @Test
    void updateUserInfo() {

        when(userRepo.findUserByEmail(updateUser.getEmail())).thenReturn(entity);
        UpdateUser result = userService.updateUserInfo(updateUser);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Alex",result.getFirstName());
        Assertions.assertEquals("Kha", result.getLastName());
        verify(userRepo, times(1)).save(entity);


    }

    @Test
    void loadUserByUsername() {
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        when(userRepo.findUserByEmail(user.getEmail())).thenReturn(entity);
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());

    }
}