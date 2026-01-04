package com.keresman.bookface.post.dao.jdbc;

import com.keresman.bookface.post.dao.PostDAO;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.post.mapping.jdbc.PostRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postJdbc")
@RequiredArgsConstructor
public class PostJDBCDataAccessService implements PostDAO {

    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper postRowMapper;

    @Override
    public List<Post> selectAllPosts() {
        var sql = """
                SELECT p.id, p.image_id, p.title, p.content, p.user_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password
                FROM post p
                JOIN app_user u ON p.user_id = u.id
                """;

        return jdbcTemplate.query(sql, postRowMapper);
    }

    @Override
    public Optional<Post> selectPostById(Long postId) {
        var sql = """
                SELECT p.id, p.image_id, p.title, p.content, p.user_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password
                FROM post p
                JOIN app_user u ON p.user_id = u.id
                WHERE p.id = ?
                """;

        return jdbcTemplate.query(sql, postRowMapper, postId)
                .stream()
                .findFirst();
    }

    @Override
    public List<Post> selectPostsByUserId(Long userId) {
        var sql = """
                SELECT p.id, p.image_id, p.title, p.content, p.user_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password
                FROM post p
                JOIN app_user u ON p.user_id = u.id
                WHERE p.user_id = ?
                """;

        return jdbcTemplate.query(sql, postRowMapper, userId);
    }

    @Override
    public void insertPost(Post post) {
        var sql = """
                INSERT INTO post (image_id, title, content, user_id)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                post.getImageId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getId()
        );
    }

    @Override
    public boolean existsPostWithId(Long id) {
        var sql = """
                SELECT COUNT(*)
                FROM post
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deletePostById(Long id) {
        var sql = """
                DELETE FROM post
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updatePost(Post post) {
        if (post.getTitle() != null) {
            var sql = """
                    UPDATE post
                    SET title = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, post.getTitle(), post.getId());
        }

        if (post.getContent() != null) {
            var sql = """
                    UPDATE post
                    SET content = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, post.getContent(), post.getId());
        }

        if (post.getImageId() != null) {
            var sql = """
                    UPDATE post
                    SET image_id = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, post.getImageId(), post.getId());
        }
    }
}