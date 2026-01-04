package com.keresman.bookface.comment.dao.jpa;

import com.keresman.bookface.comment.dao.CommentDAO;
import com.keresman.bookface.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("commentJpa")
@RequiredArgsConstructor
public class CommentJPADataAccessService implements CommentDAO {

    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> selectAllComments() {
        Page<Comment> commentsPage = commentRepository.findAll(Pageable.ofSize(DEFAULT_PAGE_SIZE));
        return commentsPage.getContent();
    }

    @Override
    public Optional<Comment> selectCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public List<Comment> selectCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public List<Comment> selectCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public void insertComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public boolean existsCommentWithId(Long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void updateComment(Comment comment) {
        commentRepository.save(comment);
    }
}