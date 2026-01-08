package com.keresman.bookface.reply.dto.response;

public record ReplyDTO(
        Long id,
        String content,
        Long userId,
        String username,
        Long commentId,
        String commentContent
) {}