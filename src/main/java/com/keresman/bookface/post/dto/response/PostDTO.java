package com.keresman.bookface.post.dto.response;

public record PostDTO(
        Long id,
        String title,
        String content,
        String imageId,
        Long userId,
        String username
) {}