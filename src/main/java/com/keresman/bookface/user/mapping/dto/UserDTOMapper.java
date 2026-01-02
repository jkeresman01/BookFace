package com.keresman.bookface.user.mapping.dto;

import com.keresman.bookface.user.dto.response.UserDTO;
import com.keresman.bookface.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getUsername());
    }

}
