package com.example.taskmanagment.mapper;

import com.example.taskmanagment.dto.comment.Comment;
import com.example.taskmanagment.entity.CommentsEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class CommentsConvertor {
    private final ModelMapper modelMapper = new ModelMapper();

    public CommentsEntity convertToEntity(Comment comment) {
        configureModelMapper();
        if (comment == null) {
            return null;
        }
        return modelMapper.map(comment, CommentsEntity.class);
    }
    private void configureModelMapper () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }
}
