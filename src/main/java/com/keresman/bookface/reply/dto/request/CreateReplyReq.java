package com.keresman.bookface.reply.dto.request;

public record CreateReplyReq(
        String content,
        Long userId,
        Long commentId
) {}