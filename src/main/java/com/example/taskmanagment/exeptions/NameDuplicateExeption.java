package com.example.taskmanagment.exeptions;

public class NameDuplicateExeption extends RuntimeException {
    public NameDuplicateExeption(String message) {
        super(message);
    }
}
