package com.keresman.bookface.comment.service;

import com.keresman.bookface.comment.dao.CommentDAO;
import com.keresman.bookface.comment.dto.request.CreateCommentReq;
import com.keresman.bookface.comment.dto.request.UpdateCommentReq;
import com.keresman.bookface.comment.dto.response.CommentDTO;
import com.keresman.bookface.comment.entity.Comment;
import com.keresman.bookface.comment.mapping.dto.CommentDTOMapper;
import com.keresman.bookface.post.dao.PostDAO;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentDAO commentDAO;
    private final UserDAO userDAO;
    private final PostDAO postDAO;
    private final CommentDTOMapper commentDTOMapper;

    public CommentService(
            @Qualifier("commentJpa") CommentDAO commentDAO,
            @Qualifier("jpaUser") UserDAO userDAO,
            @Qualifier("postJpa") PostDAO postDAO,
            CommentDTOMapper commentDTOMapper
    ) {
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.commentDTOMapper = commentDTOMapper;
    }

    public List<CommentDTO> getAllComments() {
        return commentDAO
                .selectAllComments()
                .stream()
                .map(commentDTOMapper)
                .toList();
    }

    public Optional<CommentDTO> getCommentByIdOptional(Long commentId) {
        return commentDAO
                .selectCommentById(commentId)
                .map(commentDTOMapper);
    }

    public List<CommentDTO> getCommentsByUserId(Long userId) {
        return commentDAO
                .selectCommentsByUserId(userId)
                .stream()
                .map(commentDTOMapper)
                .toList();
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentDAO
                .selectCommentsByPostId(postId)
                .stream()
                .map(commentDTOMapper)
                .toList();
    }

    public Long createComment(CreateCommentReq createCommentReq) {
        User user = userDAO
                .selectUserById(createCommentReq.userId())
                .orElseThrow(() ->
                        new RuntimeException("User with id [%d] not found".formatted(createCommentReq.userId())));

        Post post = postDAO
                .selectPostById(createCommentReq.postId())
                .orElseThrow(() ->
                        new RuntimeException("Post with id [%d] not found".formatted(createCommentReq.postId())));

        Comment comment = new Comment(
                null,  // id will be auto-generated
                createCommentReq.content(),
                user,
                post
        );

        commentDAO.insertComment(comment);

        return comment.getId();
    }

    public void updateCommentById(Long commentId, UpdateCommentReq updateCommentReq) {
        Comment comment = commentDAO
                .selectCommentById(commentId)
                .orElseThrow(() ->
                        new RuntimeException("Comment with id [%d] not found".formatted(commentId)));

        if (updateCommentReq.content() == null ||
                updateCommentReq.content().equals(comment.getContent())) {
            return;
        }

        comment.setContent(updateCommentReq.content());
        commentDAO.updateComment(comment);
    }

    public void deleteCommentById(Long commentId) {
        if (!commentDAO.existsCommentWithId(commentId)) {
            throw new RuntimeException("Comment with id [%d] not found".formatted(commentId));
        }

        commentDAO.deleteCommentById(commentId);
    }
}