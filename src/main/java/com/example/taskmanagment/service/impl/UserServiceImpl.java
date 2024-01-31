package com.example.taskmanagment.service.impl;

import com.example.taskmanagment.dto.user.UpdateUser;
import com.example.taskmanagment.entity.UserEntity;
import com.example.taskmanagment.exeptions.UserEmailNotFoundExeption;
import com.example.taskmanagment.mapper.UserConvertor;
import com.example.taskmanagment.service.UserService;
import com.example.taskmanagment.service.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
/**
 * class for working with users
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final UserConvertor userConvertor;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepo userRepo, UserConvertor userConvertor) {
        this.userRepo = userRepo;
        this.userConvertor = userConvertor;
    }

    /**
     * Updates user information based on the transmitted data.
     */
    @Transactional
    @Override
    public UpdateUser updateUserInfo(UpdateUser updateUser) {
        UserEntity user = userRepo.findUserByEmail(updateUser.getEmail());
        logger.info("User found by email: " + user);
        if (user != null) {
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            userRepo.save(user);
            logger.info("saving completed");
            return updateUser;
        } else {
            logger.warn("User not found for email: " + updateUser.getEmail());
            throw new UserEmailNotFoundExeption("mail not found");
        }


    }
    /**
     * Gets the names of all users for possible appointment of an executor
     */
    public List<String> getNamesOfAllUsers() {
        List<UserEntity> list = userRepo.findAll();
        return list.stream().map(UserEntity::getFirstName).collect(Collectors.toList());

    }
    /**
     * Gets the name of the currently authenticated user
     */

    public String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
       return user.getUsername();


    }
    /**
     * Loads the user by his email for authentication
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UserEmailNotFoundExeption {
        UserEntity user = userRepo.findUserByEmail(email);
        if (user == null) {
            throw new UserEmailNotFoundExeption("email not found" + email);

        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
