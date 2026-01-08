package com.keresman.bookface.like.dto.request;

public record CreateLikeReq(
        Long userId,
        Long postId
) {}