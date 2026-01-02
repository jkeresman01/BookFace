package com.keresman.bookface.user.service;

import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.dto.request.CreateUserReq;
import com.keresman.bookface.user.dto.request.UpdateUserReq;
import com.keresman.bookface.user.dto.response.UserDTO;
import com.keresman.bookface.user.mapping.dto.UserDTOMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserDTOMapper userDTOMapper;
    //    private final PasswordEncoder passwordEncoder;


    public UserService(@Qualifier("jdbc") UserDAO userDAO, UserDTOMapper userDTOMapper) {
        this.userDAO = userDAO;
        this.userDTOMapper = userDTOMapper;
    }

//    public UserService(@Qualifier("jpa") UserDAO userDAO, UserDTOMapper userDTOMapper, PasswordEncoder passwordEncoder) {
//        this.userDAO = userDAO;
//        this.userDTOMapper = userDTOMapper;
//        this.passwordEncoder = passwordEncoder;
//    }
//

    public List<UserDTO> getAllUsers() {
        return userDAO.selectAllUsers().stream()
                .map(userDTOMapper)
                .toList();
    }

    public UserDTO getUserById(Long userId) {
        return userDAO.selectUserById(userId)
                .map(userDTOMapper)
                .orElse(null);
//                .orElseThrow(
//                        //TODO global ex hanlder and this sweat thing
//                        () -> new ResourceNotFoundException("User with id [%d] not found".formatted(userId))
//                );
    }

    public Long createUser(CreateUserReq createUserReq) {

//        String email = createUserReq.email();
//
//        boolean isEmailTaken = userDAO.existsUserWithEmail(email);
//
//        if (isEmailTaken) {
//            throw new DuplicatedResourceException("Email [%s] is already taken!".formatted(email));
//        }
//
//        var user = new User(
//                passwordEncoder.encode(userRegistrationRequest.password()),
//                createUserReq.password(),
//                createUserReq.username(),
//                Gender.valueOf(createUserReq.gender()),
//                createUserReq.firstName(),
//                createUserReq.lastName(),
//                createUserReq.email()
//
//        );
//
//        userDAO.insertUser(user);

        return null;
    }

    public void updateUserById(Long userId, UpdateUserReq userUpdateRequest) {

    }

    public void deleteUserWithId(Long userId) {
        boolean isUserIdValid = userDAO.existsUserWithId(userId);
//
//        if (!isUserIdValid) {
//            throw new ResourceNotFoundException("User with id [%d] not found".formatted(userId));
//        }

        userDAO.deleteUserById(userId);
    }

    public byte[] getProfileImage() {
        //TODO -- don't care
        return null;
    }
}
