package com.keresman.bookface.reply.mapping.jdbc;

import com.keresman.bookface.comment.entity.Comment;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.reply.entity.Reply;
import com.keresman.bookface.user.entity.Gender;
import com.keresman.bookface.user.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ReplyRowMapper implements RowMapper<Reply> {
    @Override
    public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("username"),
                rs.getString("password")
        );

        Post post = new Post(
                rs.getLong("post_id"),
                rs.getString("image_id"),
                rs.getString("post_title"),
                rs.getString("post_content"),
                user
        );

        Comment comment = new Comment(
                rs.getLong("comment_id"),
                rs.getString("comment_content"),
                user,
                post
        );

        return new Reply(
                rs.getLong("reply_id"),
                rs.getString("reply_content"),
                user,
                comment
        );
    }
}