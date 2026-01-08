package com.keresman.bookface.reply.dao;

import com.keresman.bookface.reply.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyDAO {
    List<Reply> selectAllReplies();
    Optional<Reply> selectReplyById(Long replyId);
    List<Reply> selectRepliesByUserId(Long userId);
    List<Reply> selectRepliesByCommentId(Long commentId);
    void insertReply(Reply reply);
    boolean existsReplyWithId(Long id);
    void deleteReplyById(Long id);
    void updateReply(Reply reply);
}