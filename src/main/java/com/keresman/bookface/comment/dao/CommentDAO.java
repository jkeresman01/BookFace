package com.keresman.bookface.comment.dao;

import com.keresman.bookface.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    List<Comment> selectAllComments();
    Optional<Comment> selectCommentById(Long commentId);
    List<Comment> selectCommentsByUserId(Long userId);
    List<Comment> selectCommentsByPostId(Long postId);
    void insertComment(Comment comment);
    boolean existsCommentWithId(Long id);
    void deleteCommentById(Long id);
    void updateComment(Comment comment);
}