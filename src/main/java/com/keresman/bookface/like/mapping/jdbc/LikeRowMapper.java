package com.keresman.bookface.like.mapping.jdbc;

import com.keresman.bookface.like.entity.Like;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.user.entity.Gender;
import com.keresman.bookface.user.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LikeRowMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
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
                rs.getString("title"),
                rs.getString("content"),
                user
        );

        return new Like(
                rs.getLong("like_id"),
                user,
                post
        );
    }
}