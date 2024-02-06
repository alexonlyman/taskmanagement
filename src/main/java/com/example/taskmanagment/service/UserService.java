package com.example.taskmanagment.service;

import com.example.taskmanagment.dto.user.UpdateUser;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    UpdateUser updateUserInfo(UpdateUser updateUser);

    List<String> getAllusers = new ArrayList<>();

}
