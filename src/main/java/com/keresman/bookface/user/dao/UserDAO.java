package com.keresman.bookface.user.dao;

import com.keresman.bookface.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> selectAllUsers();
    Optional<User> selectUserById(Long userId);
    Optional<User> selectUserByUsername(String username);
    void insertUser(User user);
    boolean existsUserWithEmail(String email);
    boolean existsUserWithId(Long id);
    void deleteUserById(Long id);
    void updateUser(User user);
    Optional<User> selectUserByEmail(String email);
    void updateProfileImage(String profileImageId, Long id);
}
