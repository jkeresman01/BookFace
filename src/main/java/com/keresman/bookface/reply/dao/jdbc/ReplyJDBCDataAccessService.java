
package com.keresman.bookface.reply.dao.jdbc;

import com.keresman.bookface.reply.dao.ReplyDAO;
import com.keresman.bookface.reply.entity.Reply;
import com.keresman.bookface.reply.mapping.jdbc.ReplyRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("replyJdbc")
@RequiredArgsConstructor
public class ReplyJDBCDataAccessService implements ReplyDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ReplyRowMapper replyRowMapper;

    @Override
    public List<Reply> selectAllReplies() {
        var sql = """
                SELECT r.id AS reply_id, r.content AS reply_content,
                       r.user_id, r.comment_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       c.content AS comment_content, c.post_id,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM reply r
                JOIN app_user u ON r.user_id = u.id
                JOIN comment c ON r.comment_id = c.id
                JOIN post p ON c.post_id = p.id
                """;

        return jdbcTemplate.query(sql, replyRowMapper);
    }

    @Override
    public Optional<Reply> selectReplyById(Long replyId) {
        var sql = """
                SELECT r.id AS reply_id, r.content AS reply_content,
                       r.user_id, r.comment_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       c.content AS comment_content, c.post_id,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM reply r
                JOIN app_user u ON r.user_id = u.id
                JOIN comment c ON r.comment_id = c.id
                JOIN post p ON c.post_id = p.id
                WHERE r.id = ?
                """;

        return jdbcTemplate.query(sql, replyRowMapper, replyId)
                .stream()
                .findFirst();
    }

    @Override
    public List<Reply> selectRepliesByUserId(Long userId) {
        var sql = """
                SELECT r.id AS reply_id, r.content AS reply_content,
                       r.user_id, r.comment_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       c.content AS comment_content, c.post_id,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM reply r
                JOIN app_user u ON r.user_id = u.id
                JOIN comment c ON r.comment_id = c.id
                JOIN post p ON c.post_id = p.id
                WHERE r.user_id = ?
                """;

        return jdbcTemplate.query(sql, replyRowMapper, userId);
    }

    @Override
    public List<Reply> selectRepliesByCommentId(Long commentId) {
        var sql = """
                SELECT r.id AS reply_id, r.content AS reply_content,
                       r.user_id, r.comment_id,
                       u.first_name, u.last_name, u.email, u.gender, u.username, u.password,
                       c.content AS comment_content, c.post_id,
                       p.image_id, p.title AS post_title, p.content AS post_content
                FROM reply r
                JOIN app_user u ON r.user_id = u.id
                JOIN comment c ON r.comment_id = c.id
                JOIN post p ON c.post_id = p.id
                WHERE r.comment_id = ?
                """;

        return jdbcTemplate.query(sql, replyRowMapper, commentId);
    }

    @Override
    public void insertReply(Reply reply) {
        var sql = """
                INSERT INTO reply (content, user_id, comment_id)
                VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(sql,
                reply.getContent(),
                reply.getUser().getId(),
                reply.getComment().getId()
        );
    }

    @Override
    public boolean existsReplyWithId(Long id) {
        var sql = """
                SELECT COUNT(*)
                FROM reply
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteReplyById(Long id) {
        var sql = """
                DELETE FROM reply
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateReply(Reply reply) {
        if (reply.getContent() != null) {
            var sql = """
                    UPDATE reply
                    SET content = ?
                    WHERE id = ?
                    """;

            jdbcTemplate.update(sql, reply.getContent(), reply.getId());
        }
    }
}