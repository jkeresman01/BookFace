package com.keresman.bookface.post.dto.request;

public record CreatePostReq(
        String title,
        String content,
        String imageId,
        Long userId
) {}