package com.keresman.bookface.user.service;

import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.dto.request.CreateUserReq;
import com.keresman.bookface.user.dto.request.UpdateUserReq;
import com.keresman.bookface.user.dto.response.UserDTO;
import com.keresman.bookface.user.entity.Gender;
import com.keresman.bookface.user.entity.User;
import com.keresman.bookface.user.mapping.dto.UserDTOMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserDTOMapper userDTOMapper;


    public UserService(@Qualifier("jdbcUser") UserDAO userDAO, UserDTOMapper userDTOMapper) {
        this.userDAO = userDAO;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userDAO
                .selectAllUsers().stream()
                .map(userDTOMapper)
                .toList();
    }

    public Optional<UserDTO> getUserById(Long userId) {
        return userDAO
                .selectUserById(userId)
                .map(userDTOMapper);
    }

    public Long createUser(CreateUserReq createUserReq) {
        String email = createUserReq.email();

        boolean isEmailTaken = userDAO.existsUserWithEmail(email);

        if (isEmailTaken) {
            throw new RuntimeException("Email [%s] is already taken!".formatted(email));
        }

        var user = new User(
                null,
                createUserReq.firstName(),
                createUserReq.lastName(),
                createUserReq.email(),
                Gender.valueOf(createUserReq.gender().toUpperCase()),
                createUserReq.username(),
                createUserReq.password()  // In non-imaginary application hash this thingy
        );

        userDAO.insertUser(user);

        return userDAO.selectUserByEmail(email)
                .map(User::getId)
                .orElse(null);
    }

    public void updateUserById(Long userId, UpdateUserReq userUpdateRequest) {
        var user = userDAO.selectUserById(userId)
                .orElseThrow(() -> new RuntimeException("User with id [%d] not found".formatted(userId)));

        boolean changes = false;

        if (userUpdateRequest.firstName() != null && !userUpdateRequest.firstName().equals(user.getFirstName())) {
            user.setFirstName(userUpdateRequest.firstName());
            changes = true;
        }

        if (userUpdateRequest.lastName() != null && !userUpdateRequest.lastName().equals(user.getLastName())) {
            user.setLastName(userUpdateRequest.lastName());
            changes = true;
        }

        if (userUpdateRequest.email() != null && !userUpdateRequest.email().equals(user.getEmail())) {
            if (userDAO.existsUserWithEmail(userUpdateRequest.email())) {
                throw new RuntimeException("Email already taken");
            }
            user.setEmail(userUpdateRequest.email());
            changes = true;
        }

        if (userUpdateRequest.username() != null && !userUpdateRequest.username().equals(user.getUsername())) {
            user.setUsername(userUpdateRequest.username());
            changes = true;
        }

        if (userUpdateRequest.gender() != null) {
            Gender newGender = Gender.valueOf(userUpdateRequest.gender().toUpperCase());
            if (!newGender.equals(user.getGender())) {
                user.setGender(newGender);
                changes = true;
            }
        }

        if (userUpdateRequest.password() != null && !userUpdateRequest.password().equals(user.getPassword())) {
            user.setPassword(userUpdateRequest.password());  // In non-imaginary application hash this thingy
            changes = true;
        }

        if (!changes) {
            return;
        }

        userDAO.updateUser(user);
    }

    public void deleteUserWithId(Long userId) {
        if (!userDAO.existsUserWithId(userId)) {
            throw new RuntimeException("User with id [%d] not found".formatted(userId));
        }

        userDAO.deleteUserById(userId);
    }

    public byte[] getProfileImage() {
        //TODO -- don't care
        return null;
    }
}
