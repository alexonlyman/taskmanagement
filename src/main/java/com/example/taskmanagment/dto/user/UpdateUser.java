package com.example.taskmanagment.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateUser {
    private String firstName;
    private String lastName;
    private String email;
}
