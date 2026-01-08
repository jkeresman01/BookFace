package com.keresman.bookface.like.dto.response;

public record LikeDTO(
        Long id,
        Long userId,
        String username,
        Long postId,
        String postTitle
) {}