package com.keresman.bookface.post.mapping.dto;

import com.keresman.bookface.post.dto.response.PostDTO;
import com.keresman.bookface.post.entity.Post;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PostDTOMapper implements Function<Post, PostDTO> {

    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageId(),
                post.getUser().getId(),
                post.getUser().getUsername()
        );
    }
}