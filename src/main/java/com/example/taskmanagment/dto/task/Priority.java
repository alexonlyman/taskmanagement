package com.example.taskmanagment.dto.task;

public enum Priority {
    LOW_PRIORITY("low"),
    MEDIUM_PRIORITY("medium"),
    HIGH_PRIORITY("high");

    public final String value;
    Priority(String value) {
        this.value = value;
    }
}
