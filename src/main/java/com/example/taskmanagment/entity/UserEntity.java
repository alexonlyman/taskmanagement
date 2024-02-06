package com.example.taskmanagment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class UserEntity {
    public UserEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    @Column(name = "firstName")
    private String firstName;
    @Getter
    @Setter
    @Column(name = "lastName")
    private String lastName;
    @Getter
    @Setter
    @Email
    @Column(name = "email", unique = true)
    private String email;
    @Getter
    @Setter
    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    private List <TaskEntity> task;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CommentsEntity> commentsEntityList = new ArrayList<>();

}
