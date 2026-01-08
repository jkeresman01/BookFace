package com.keresman.bookface.user.dao.jpa;

import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userJPA")
@RequiredArgsConstructor
public class UserJPADataAccessService implements UserDAO {

    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final UserRepository userRepository;

    @Override
    public List<User> selectAllUsers() {
        Page<User> usersPage = userRepository.findAll(Pageable.ofSize(DEFAULT_PAGE_SIZE));
        return usersPage.getContent();
    }

    @Override
    public Optional<User> selectUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsUserWithId(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateProfileImage(String profileImageId, Long id) {
        //TODO banana
    }
}
