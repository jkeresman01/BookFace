package com.keresman.bookface.user.dto.response;

import com.keresman.bookface.user.entity.Gender;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        String username
) { }
