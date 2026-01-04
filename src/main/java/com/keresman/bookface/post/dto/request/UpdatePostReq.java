package com.keresman.bookface.post.dto.request;

public record UpdatePostReq(
        String title,
        String content,
        String imageId
) {}