package com.keresman.bookface.reply.dao.jpa;

import com.keresman.bookface.reply.dao.ReplyDAO;
import com.keresman.bookface.reply.entity.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("replyJpa")
@RequiredArgsConstructor
public class ReplyJPADataAccessService implements ReplyDAO {

    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final ReplyRepository replyRepository;

    @Override
    public List<Reply> selectAllReplies() {
        Page<Reply> repliesPage = replyRepository.findAll(Pageable.ofSize(DEFAULT_PAGE_SIZE));
        return repliesPage.getContent();
    }

    @Override
    public Optional<Reply> selectReplyById(Long replyId) {
        return replyRepository.findById(replyId);
    }

    @Override
    public List<Reply> selectRepliesByUserId(Long userId) {
        return replyRepository.findByUserId(userId);
    }

    @Override
    public List<Reply> selectRepliesByCommentId(Long commentId) {
        return replyRepository.findByCommentId(commentId);
    }

    @Override
    public void insertReply(Reply reply) {
        replyRepository.save(reply);
    }

    @Override
    public boolean existsReplyWithId(Long id) {
        return replyRepository.existsById(id);
    }

    @Override
    public void deleteReplyById(Long id) {
        replyRepository.deleteById(id);
    }

    @Override
    public void updateReply(Reply reply) {
        replyRepository.save(reply);
    }
}