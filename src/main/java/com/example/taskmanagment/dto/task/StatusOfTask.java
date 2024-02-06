package com.example.taskmanagment.dto.task;

public enum StatusOfTask {
    EXPECTATION("expectation"),
    IN_PROCESS("in process"),
    COMPLETE("complete");

    public final String value;
    StatusOfTask (String value) {
        this.value = value;
    }


}
