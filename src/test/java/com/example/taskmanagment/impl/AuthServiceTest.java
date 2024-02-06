package com.example.taskmanagment.impl;

import com.example.taskmanagment.dto.auth.Register;
import com.example.taskmanagment.dto.user.User;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.exeptions.WrongLoginPasswordExeption;
import com.example.taskmanagment.mapper.UserConvertor;
import com.example.taskmanagment.service.impl.AuthService;
import com.example.taskmanagment.service.repository.UserRepo;
import com.example.taskmanagment.utils.AuthenticationResponse;
import com.example.taskmanagment.utils.JwtTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private AuthenticationManager manager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserConvertor userConvertor;

    @Mock
    private JwtTokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register() {
        Register register = new Register("test@mail.ru", "passwordTest");
        UserEntity userEntity = new UserEntity();
        when(userConvertor.converToEntity(any(User.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(tokenService.generateToken(any(User.class))).thenReturn("testToken");

        AuthenticationResponse response = authService.register(register);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("testToken", response.getToken());

        verify(userConvertor, times(1)).converToEntity(any(User.class));
        verify(userRepo, times(1)).save(userEntity);
        verify(passwordEncoder, times(1)).encode("passwordTest");
        verify(tokenService, times(1)).generateToken(any(User.class));
    }

    @Test
    void authentication() throws WrongLoginPasswordExeption {
        Register register = new Register("testmail@test.com", "testPassword");
        User user = User.builder()
                .email("testmail@test.com")
                .password("testPassword")
                .build();
        UserEntity userEntity = new UserEntity();
        when(userRepo.findUserByEmail(register.getEmail())).thenReturn(userEntity);
        when(userConvertor.convertToDto(userEntity)).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("testToken");

        AuthenticationResponse response = authService.authentication(register);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("testToken", response.getToken());

        verify(manager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepo, times(1)).findUserByEmail(register.getEmail());
        verify(userConvertor, times(1)).convertToDto(userEntity);
        verify(tokenService, times(1)).generateToken(user);



    }
}