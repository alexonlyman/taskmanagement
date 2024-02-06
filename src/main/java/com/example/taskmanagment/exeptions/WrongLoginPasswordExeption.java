package com.example.taskmanagment.exeptions;

public class WrongLoginPasswordExeption extends Exception {
    public WrongLoginPasswordExeption(String message) {
        super(message);
    }
}

