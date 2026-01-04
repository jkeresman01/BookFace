package com.keresman.bookface.comment.mapping.dto;

import com.keresman.bookface.comment.dto.response.CommentDTO;
import com.keresman.bookface.comment.entity.Comment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommentDTOMapper implements Function<Comment, CommentDTO> {

    @Override
    public CommentDTO apply(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getPost().getId(),
                comment.getPost().getTitle()
        );
    }
}