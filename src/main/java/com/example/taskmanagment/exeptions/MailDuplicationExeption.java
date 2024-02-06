package com.example.taskmanagment.exeptions;

public class MailDuplicationExeption extends RuntimeException {
    public MailDuplicationExeption(String message) {
        super(message);
    }
}
