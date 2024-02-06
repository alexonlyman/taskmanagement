package com.example.taskmanagment.dto.comment;

import lombok.Data;

import java.util.Objects;

@Data
public class Comment {
    private Integer id;
    private String comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(id, comment1.id) && Objects.equals(comments, comment1.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comments);
    }
}
