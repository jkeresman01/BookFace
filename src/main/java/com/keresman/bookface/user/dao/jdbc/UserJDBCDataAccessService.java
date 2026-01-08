package com.keresman.bookface.user.dao.jdbc;

import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import com.keresman.bookface.user.mapping.jdbc.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbcUser")
@RequiredArgsConstructor
public class UserJDBCDataAccessService implements UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;

    @Override
    public List<User> selectAllUsers() {
        var sql = """
                 SELECT id, first_name, last_name, email, gender, username, password
                 FROM app_user
                """;

        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Long userId) {
        var sql = """
                SELECT id, first_name, last_name, email, gender, username, password
                FROM app_user
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, userRowMapper, userId)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        var sql = """
                SELECT id, first_name, last_name, email, gender, username, password
                FROM app_user
                WHERE username = ?
                """;

        return jdbcTemplate.query(sql, userRowMapper, username)
                .stream()
                .findFirst();
    }

    @Override
    public void insertUser(User user) {
        var sql = """
                INSERT INTO app_user (first_name, last_name, email, gender, username, password)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        int rowsAffected = jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getGender().name(),
                user.getUsername(),
                user.getPassword()
        );

        System.out.println("Rows affected: %d".formatted(rowsAffected));
    }

    @Override
    public boolean existsUserWithEmail(String email) {
        var sql = """
                SELECT COUNT(*)
                FROM app_user
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsUserWithId(Long id) {
        var sql = """
                SELECT COUNT(*)
                FROM app_user
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteUserById(Long id) {
        var sql = """
                DELETE FROM app_user
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        if (user.getFirstName() != null) {
            var sql = """
                    UPDATE app_user
                    SET first_name = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getFirstName(), user.getId());
        }

        if (user.getLastName() != null) {
            var sql = """
                    UPDATE app_user
                    SET last_name = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getLastName(), user.getId());
        }

        if (user.getGender() != null) {
            var sql = """
                    UPDATE app_user
                    SET gender = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getGender().name(), user.getId());
        }

        if (user.getUsername() != null) {
            var sql = """
                    UPDATE app_user
                    SET username = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getUsername(), user.getId());
        }

        if (user.getEmail() != null) {
            var sql = """
                    UPDATE app_user
                    SET email = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getEmail(), user.getId());
        }

        if (user.getPassword() != null) {
            var sql = """
                    UPDATE app_user
                    SET password = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, user.getPassword(), user.getId());
        }
    }

    @Override
    public Optional<User> selectUserByEmail(String email) {
        var sql = """
                SELECT id, first_name, last_name, email, gender, username, password
                FROM app_user
                WHERE email = ?
                """;

        return jdbcTemplate.query(sql, userRowMapper, email)
                .stream()
                .findFirst();
    }

    @Override
    public void updateProfileImage(String profileImageId, Long id) {

    }
}
