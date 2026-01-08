package com.keresman.bookface.like.dao.jdbc;

import com.keresman.bookface.like.dao.LikeDAO;
import com.keresman.bookface.like.entity.Like;
import com.keresman.bookface.like.mapping.jdbc.LikeRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("likeJdbc")
@RequiredArgsConstructor
public class LikeJDBCDataAccessService implements LikeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final LikeRowMapper likeRowMapper;

    @Override
    public List<Like> selectAllLikes() {
        var sql = """
                SELECT l.id AS like_id, l.user_id, l.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title, p.content
                FROM likes l
                JOIN app_user u ON l.user_id = u.id
                JOIN post p ON l.post_id = p.id
                """;

        return jdbcTemplate.query(sql, likeRowMapper);
    }

    @Override
    public Optional<Like> selectLikeById(Long likeId) {
        var sql = """
                SELECT l.id AS like_id, l.user_id, l.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title, p.content
                FROM likes l
                JOIN app_user u ON l.user_id = u.id
                JOIN post p ON l.post_id = p.id
                WHERE l.id = ?
                """;

        return jdbcTemplate.query(sql, likeRowMapper, likeId)
                .stream()
                .findFirst();
    }

    @Override
    public List<Like> selectLikesByUserId(Long userId) {
        var sql = """
                SELECT l.id AS like_id, l.user_id, l.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title, p.content
                FROM likes l
                JOIN app_user u ON l.user_id = u.id
                JOIN post p ON l.post_id = p.id
                WHERE l.user_id = ?
                """;

        return jdbcTemplate.query(sql, likeRowMapper, userId);
    }

    @Override
    public List<Like> selectLikesByPostId(Long postId) {
        var sql = """
                SELECT l.id AS like_id, l.user_id, l.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title, p.content
                FROM likes l
                JOIN app_user u ON l.user_id = u.id
                JOIN post p ON l.post_id = p.id
                WHERE l.post_id = ?
                """;

        return jdbcTemplate.query(sql, likeRowMapper, postId);
    }

    @Override
    public Optional<Like> selectLikeByUserAndPost(Long userId, Long postId) {
        var sql = """
                SELECT l.id AS like_id, l.user_id, l.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title, p.content
                FROM likes l
                JOIN app_user u ON l.user_id = u.id
                JOIN post p ON l.post_id = p.id
                WHERE l.user_id = ? AND l.post_id = ?
                """;

        return jdbcTemplate.query(sql, likeRowMapper, userId, postId)
                .stream()
                .findFirst();
    }

    @Override
    public void insertLike(Like like) {
        var sql = """
                INSERT INTO likes (user_id, post_id)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(sql,
                like.getUser().getId(),
                like.getPost().getId()
        );
    }

    @Override
    public boolean existsLikeWithId(Long id) {
        var sql = """
                SELECT COUNT(*)
                FROM likes
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsLikeByUserAndPost(Long userId, Long postId) {
        var sql = """
                SELECT COUNT(*)
                FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, postId);
        return count != null && count > 0;
    }

    @Override
    public void deleteLikeById(Long id) {
        var sql = """
                DELETE FROM likes
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteLikeByUserAndPost(Long userId, Long postId) {
        var sql = """
                DELETE FROM likes
                WHERE user_id = ? AND post_id = ?
                """;

        jdbcTemplate.update(sql, userId, postId);
    }

    @Override
    public int countLikesByPostId(Long postId) {
        var sql = """
                SELECT COUNT(*)
                FROM likes
                WHERE post_id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, postId);
        return count != null ? count : 0;
    }
}