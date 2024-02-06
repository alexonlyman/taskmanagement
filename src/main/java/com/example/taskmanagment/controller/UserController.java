package com.example.taskmanagment.controller;

import com.example.taskmanagment.dto.user.UpdateUser;
import com.example.taskmanagment.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@AllArgsConstructor
@CrossOrigin(value = "http://localhost:8080")
public class UserController {
    private final UserServiceImpl userService;
    @Operation(summary = "получение списока пользователей", description = "получает список имен всех пользователей", tags={ "пользователь" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @PostMapping("/updateUserInfo")
    public ResponseEntity<UpdateUser> updateUserInfo(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok(userService.updateUserInfo(updateUser));
    }

    @Operation(summary = "получение списока пользователей", description = "получает список имен всех пользователей", tags={ "пользователь" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @GetMapping("/getUsers")
    private ResponseEntity<?> usersList() {
        return ResponseEntity.ok(userService.getNamesOfAllUsers());
    }

}
