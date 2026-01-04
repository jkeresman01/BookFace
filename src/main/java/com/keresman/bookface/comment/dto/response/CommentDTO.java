package com.keresman.bookface.comment.dto.response;

public record CommentDTO(
        Long id,
        String content,
        Long userId,
        String username,
        Long postId,
        String postTitle
) {
}
