package com.keresman.bookface.reply.service;

import com.keresman.bookface.comment.dao.CommentDAO;
import com.keresman.bookface.comment.entity.Comment;
import com.keresman.bookface.reply.dao.ReplyDAO;
import com.keresman.bookface.reply.dto.request.CreateReplyReq;
import com.keresman.bookface.reply.dto.request.UpdateReplyReq;
import com.keresman.bookface.reply.dto.response.ReplyDTO;
import com.keresman.bookface.reply.entity.Reply;
import com.keresman.bookface.reply.mapping.dto.ReplyDTOMapper;
import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyService {

    private final ReplyDAO replyDAO;
    private final UserDAO userDAO;
    private final CommentDAO commentDAO;
    private final ReplyDTOMapper replyDTOMapper;

    public ReplyService(
            @Qualifier("replyJpa") ReplyDAO replyDAO,
            @Qualifier("userJPA") UserDAO userDAO,
            @Qualifier("commentJpa") CommentDAO commentDAO,
            ReplyDTOMapper replyDTOMapper
    ) {
        this.replyDAO = replyDAO;
        this.userDAO = userDAO;
        this.commentDAO = commentDAO;
        this.replyDTOMapper = replyDTOMapper;
    }

    public List<ReplyDTO> getAllReplies() {
        return replyDAO
                .selectAllReplies()
                .stream()
                .map(replyDTOMapper)
                .toList();
    }

    public Optional<ReplyDTO> getReplyByIdOptional(Long replyId) {
        return replyDAO
                .selectReplyById(replyId)
                .map(replyDTOMapper);
    }

    public List<ReplyDTO> getRepliesByUserId(Long userId) {
        return replyDAO
                .selectRepliesByUserId(userId)
                .stream()
                .map(replyDTOMapper)
                .toList();
    }

    public List<ReplyDTO> getRepliesByCommentId(Long commentId) {
        return replyDAO
                .selectRepliesByCommentId(commentId)
                .stream()
                .map(replyDTOMapper)
                .toList();
    }

    public Long createReply(CreateReplyReq createReplyReq) {
        User user = userDAO
                .selectUserById(createReplyReq.userId())
                .orElseThrow(() ->
                        new RuntimeException("User with id [%d] not found".formatted(createReplyReq.userId())));

        Comment comment = commentDAO
                .selectCommentById(createReplyReq.commentId())
                .orElseThrow(() ->
                        new RuntimeException("Comment with id [%d] not found".formatted(createReplyReq.commentId())));

        Reply reply = new Reply(
                null,  // id will be auto-generated
                createReplyReq.content(),
                user,
                comment
        );

        replyDAO.insertReply(reply);

        return reply.getId();
    }

    public void updateReplyById(Long replyId, UpdateReplyReq updateReplyReq) {
        Reply reply = replyDAO
                .selectReplyById(replyId)
                .orElseThrow(() ->
                        new RuntimeException("Reply with id [%d] not found".formatted(replyId)));

        if (updateReplyReq.content() == null ||
                updateReplyReq.content().equals(reply.getContent())) {
            return;
        }

        reply.setContent(updateReplyReq.content());
        replyDAO.updateReply(reply);
    }

    public void deleteReplyById(Long replyId) {
        if (!replyDAO.existsReplyWithId(replyId)) {
            throw new RuntimeException("Reply with id [%d] not found".formatted(replyId));
        }

        replyDAO.deleteReplyById(replyId);
    }
}