package com.keresman.bookface.user.dto.request;

public record UpdateUserReq(
        String firstName,
        String lastName,
        String email,
        String gender,
        String username,
        String password
) {}