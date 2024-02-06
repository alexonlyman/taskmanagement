package com.example.taskmanagment.controller;

import com.example.taskmanagment.dto.task.Task;
import com.example.taskmanagment.entity.TaskEntity;
import com.example.taskmanagment.exeptions.IdNotFoundExeption;
import com.example.taskmanagment.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;


    @Operation(summary = "создание задачи", description = "создает задачу", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PostMapping("/create")
    public ResponseEntity<?> taskCreate(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }


    @Operation(summary = "обновление задачи", description = "обновляет задачу", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PatchMapping("/updateTask/{id}")
    public ResponseEntity<?> taskUpdate(@PathVariable Integer id, @RequestBody Task task) throws IdNotFoundExeption {
        return ResponseEntity.ok(taskService.updateTask(id, task));

    }

    @Operation(summary = "удаление задачи", description = "удаляет задачу", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @DeleteMapping("/deleteTask/{id}")
    public ResponseEntity<?> deleteTaskbyId(@PathVariable Integer id) throws IdNotFoundExeption {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "добавление комментария", description = "добавляет комментарий к задаче", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PostMapping("/addComment/{id}")
    public ResponseEntity<?> addnewComment(@PathVariable Integer id, @RequestBody String comment) throws IdNotFoundExeption {
        taskService.addComment(id, comment);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "получение списка задач", description = "получаем список всех задач", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @GetMapping("/getAllTasks")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }


    @Operation(summary = "назначение исполнителя", description = "назначаем исполнителя", tags={ "задачи" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unautorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden") })
    @PutMapping("/appointment/{id}")
    public ResponseEntity<?> executor(@PathVariable Integer id,@RequestBody String name) {
        taskService.appointAnExecutor(name, id);
        return ResponseEntity.ok().body("назначен " + name);
    }


}
