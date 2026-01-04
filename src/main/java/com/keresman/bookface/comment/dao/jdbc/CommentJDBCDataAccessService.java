package com.keresman.bookface.comment.dao.jdbc;

import com.keresman.bookface.comment.dao.CommentDAO;
import com.keresman.bookface.comment.entity.Comment;
import com.keresman.bookface.comment.mapping.jdbc.CommentRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("commentJdbc")
@RequiredArgsConstructor
public class CommentJDBCDataAccessService implements CommentDAO {

    private final JdbcTemplate jdbcTemplate;
    private final CommentRowMapper commentRowMapper;

    @Override
    public List<Comment> selectAllComments() {
        var sql = """
                SELECT c.id AS comment_id, c.content AS comment_content, 
                       c.user_id, c.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM comment c
                JOIN app_user u ON c.user_id = u.id
                JOIN post p ON c.post_id = p.id
                """;

        return jdbcTemplate.query(sql, commentRowMapper);
    }

    @Override
    public Optional<Comment> selectCommentById(Long commentId) {
        var sql = """
                SELECT c.id AS comment_id, c.content AS comment_content, 
                       c.user_id, c.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM comment c
                JOIN app_user u ON c.user_id = u.id
                JOIN post p ON c.post_id = p.id
                WHERE c.id = ?
                """;

        return jdbcTemplate.query(sql, commentRowMapper, commentId)
                .stream()
                .findFirst();
    }

    @Override
    public List<Comment> selectCommentsByUserId(Long userId) {
        var sql = """
                SELECT c.id AS comment_id, c.content AS comment_content, 
                       c.user_id, c.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM comment c
                JOIN app_user u ON c.user_id = u.id
                JOIN post p ON c.post_id = p.id
                WHERE c.user_id = ?
                """;

        return jdbcTemplate.query(sql, commentRowMapper, userId);
    }

    @Override
    public List<Comment> selectCommentsByPostId(Long postId) {
        var sql = """
                SELECT c.id AS comment_id, c.content AS comment_content, 
                       c.user_id, c.post_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM comment c
                JOIN app_user u ON c.user_id = u.id
                JOIN post p ON c.post_id = p.id
                WHERE c.post_id = ?
                """;

        return jdbcTemplate.query(sql, commentRowMapper, postId);
    }

    @Override
    public void insertComment(Comment comment) {
        var sql = """
                INSERT INTO comment (content, user_id, post_id)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                comment.getContent(),
                comment.getUser().getId(),
                comment.getPost().getId()
        );
    }

    @Override
    public boolean existsCommentWithId(Long id) {
        var sql = """
                SELECT COUNT(*)
                FROM comment
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCommentById(Long id) {
        var sql = """
                DELETE FROM comment
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateComment(Comment comment) {
        if (comment.getContent() != null) {
            var sql = """
                    UPDATE comment
                    SET content = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, comment.getContent(), comment.getId());
        }
    }
}