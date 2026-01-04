package com.keresman.bookface.post.dao;

import com.keresman.bookface.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostDAO {
    List<Post> selectAllPosts();
    Optional<Post> selectPostById(Long postId);
    List<Post> selectPostsByUserId(Long userId);
    void insertPost(Post post);
    boolean existsPostWithId(Long id);
    void deletePostById(Long id);
    void updatePost(Post post);
}