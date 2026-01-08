package com.keresman.bookface.like.dao.jpa;

import com.keresman.bookface.like.dao.LikeDAO;
import com.keresman.bookface.like.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository("likeJpa")
@RequiredArgsConstructor
public class LikeJPADataAccessService implements LikeDAO {

    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final LikeRepository likeRepository;

    @Override
    public List<Like> selectAllLikes() {
        Page<Like> likesPage = likeRepository.findAll(Pageable.ofSize(DEFAULT_PAGE_SIZE));
        return likesPage.getContent();
    }

    @Override
    public Optional<Like> selectLikeById(Long likeId) {
        return likeRepository.findById(likeId);
    }

    @Override
    public List<Like> selectLikesByUserId(Long userId) {
        return likeRepository.findByUserId(userId);
    }

    @Override
    public List<Like> selectLikesByPostId(Long postId) {
        return likeRepository.findByPostId(postId);
    }

    @Override
    public Optional<Like> selectLikeByUserAndPost(Long userId, Long postId) {
        return likeRepository.findByUserIdAndPostId(userId, postId);
    }

    @Override
    public void insertLike(Like like) {
        likeRepository.save(like);
    }

    @Override
    public boolean existsLikeWithId(Long id) {
        return likeRepository.existsById(id);
    }

    @Override
    public boolean existsLikeByUserAndPost(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }

    @Override
    public void deleteLikeById(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteLikeByUserAndPost(Long userId, Long postId) {
        likeRepository.deleteByUserIdAndPostId(userId, postId);
    }

    @Override
    public int countLikesByPostId(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}