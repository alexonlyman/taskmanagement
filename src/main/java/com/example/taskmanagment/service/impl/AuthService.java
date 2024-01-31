package com.example.taskmanagment.service.impl;

import com.example.taskmanagment.dto.auth.Register;
import com.example.taskmanagment.dto.user.User;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.exeptions.WrongLoginPasswordExeption;
import com.example.taskmanagment.mapper.UserConvertor;
import com.example.taskmanagment.service.repository.UserRepo;
import com.example.taskmanagment.utils.AuthenticationResponse;
import com.example.taskmanagment.utils.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * registration and authentication class
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final JwtTokenService tokenService;

    private final UserConvertor userConvertor;

    private final AuthenticationManager manager;

    /**
     * registers a user with parameters from dto,
     * converting to entity and saving in repository
     */
    public AuthenticationResponse register(Register register) {
        logger.info("register data " + register);
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .build();
        UserEntity entity = userConvertor.converToEntity(user);
        userRepo.save(entity);
        logger.info("saving completed");
        String token = tokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }
    /**
     * user authentication using registration parameters,
     * converting to dto and generating jwt token
     */
    public AuthenticationResponse authentication(Register register) throws WrongLoginPasswordExeption {
        logger.info("authentication data "+ register);
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(register.getEmail(),
                        register.getPassword())
        );
        UserEntity userEntity = userRepo.findUserByEmail(register.getEmail());
        User user = userConvertor.convertToDto(userEntity);
        String token = tokenService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();

    }
}
