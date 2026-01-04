package com.keresman.bookface.comment.dto.request;

public record CreateCommentReq(
        String content,
        Long userId,
        Long postId
) {}