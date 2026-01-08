package com.keresman.bookface.like.mapping.dto;

import com.keresman.bookface.like.dto.response.LikeDTO;
import com.keresman.bookface.like.entity.Like;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class LikeDTOMapper implements Function<Like, LikeDTO> {

    @Override
    public LikeDTO apply(Like like) {
        return new LikeDTO(
                like.getId(),
                like.getUser().getId(),
                like.getUser().getUsername(),
                like.getPost().getId(),
                like.getPost().getTitle()
        );
    }
}