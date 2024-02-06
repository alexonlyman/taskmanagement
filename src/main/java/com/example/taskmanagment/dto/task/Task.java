package com.example.taskmanagment.dto.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    private String name;
    private StatusOfTask statusOfTask;
    private Priority priority;
    private String description;
    private String executor;


}

