package com.keresman.bookface.like.dao;

import com.keresman.bookface.like.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeDAO {
    List<Like> selectAllLikes();
    Optional<Like> selectLikeById(Long likeId);
    List<Like> selectLikesByUserId(Long userId);
    List<Like> selectLikesByPostId(Long postId);
    Optional<Like> selectLikeByUserAndPost(Long userId, Long postId);
    void insertLike(Like like);
    boolean existsLikeWithId(Long id);
    boolean existsLikeByUserAndPost(Long userId, Long postId);
    void deleteLikeById(Long id);
    void deleteLikeByUserAndPost(Long userId, Long postId);
    int countLikesByPostId(Long postId);
}