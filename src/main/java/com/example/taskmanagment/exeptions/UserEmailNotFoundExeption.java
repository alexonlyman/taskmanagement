package com.example.taskmanagment.exeptions;

public class UserEmailNotFoundExeption extends RuntimeException {
    public UserEmailNotFoundExeption(String message) {
        super(message);
    }
}
