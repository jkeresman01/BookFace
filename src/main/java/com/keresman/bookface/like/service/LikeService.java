package com.keresman.bookface.like.service;

import com.keresman.bookface.like.dao.LikeDAO;
import com.keresman.bookface.like.dto.request.CreateLikeReq;
import com.keresman.bookface.like.dto.response.LikeDTO;
import com.keresman.bookface.like.entity.Like;
import com.keresman.bookface.like.mapping.dto.LikeDTOMapper;
import com.keresman.bookface.post.dao.PostDAO;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeDAO likeDAO;
    private final UserDAO userDAO;
    private final PostDAO postDAO;
    private final LikeDTOMapper likeDTOMapper;

    public LikeService(
            @Qualifier("likeJpa") LikeDAO likeDAO,
            @Qualifier("userJPA") UserDAO userDAO,
            @Qualifier("postJpa") PostDAO postDAO,
            LikeDTOMapper likeDTOMapper
    ) {
        this.likeDAO = likeDAO;
        this.userDAO = userDAO;
        this.postDAO = postDAO;
        this.likeDTOMapper = likeDTOMapper;
    }

    public List<LikeDTO> getAllLikes() {
        return likeDAO
                .selectAllLikes()
                .stream()
                .map(likeDTOMapper)
                .toList();
    }

    public Optional<LikeDTO> getLikeByIdOptional(Long likeId) {
        return likeDAO
                .selectLikeById(likeId)
                .map(likeDTOMapper);
    }

    public List<LikeDTO> getLikesByUserId(Long userId) {
        return likeDAO
                .selectLikesByUserId(userId)
                .stream()
                .map(likeDTOMapper)
                .toList();
    }

    public List<LikeDTO> getLikesByPostId(Long postId) {
        return likeDAO
                .selectLikesByPostId(postId)
                .stream()
                .map(likeDTOMapper)
                .toList();
    }

    public int getLikesCountByPostId(Long postId) {
        return likeDAO.countLikesByPostId(postId);
    }

    public Long createLike(CreateLikeReq createLikeReq) {
        if (likeDAO.existsLikeByUserAndPost(createLikeReq.userId(), createLikeReq.postId())) {
            throw new RuntimeException("User already liked this post");
        }

        User user = userDAO
                .selectUserById(createLikeReq.userId())
                .orElseThrow(() ->
                        new RuntimeException("User with id [%d] not found".formatted(createLikeReq.userId())));

        Post post = postDAO
                .selectPostById(createLikeReq.postId())
                .orElseThrow(() ->
                        new RuntimeException("Post with id [%d] not found".formatted(createLikeReq.postId())));

        Like like = new Like(
                null,  // id will be auto-generated
                user,
                post
        );

        likeDAO.insertLike(like);

        return like.getId();
    }

    public void deleteLikeById(Long likeId) {
        if (!likeDAO.existsLikeWithId(likeId)) {
            throw new RuntimeException("Like with id [%d] not found".formatted(likeId));
        }

        likeDAO.deleteLikeById(likeId);
    }

    public void toggleLike(Long userId, Long postId) {
        if (likeDAO.existsLikeByUserAndPost(userId, postId)) {
            // Unlike
            likeDAO.deleteLikeByUserAndPost(userId, postId);
        } else {
            // Like
            User user = userDAO
                    .selectUserById(userId)
                    .orElseThrow(() ->
                            new RuntimeException("User with id [%d] not found".formatted(userId)));

            Post post = postDAO
                    .selectPostById(postId)
                    .orElseThrow(() ->
                            new RuntimeException("Post with id [%d] not found".formatted(postId)));

            Like like = new Like(null, user, post);
            likeDAO.insertLike(like);
        }
    }

    public boolean hasUserLikedPost(Long userId, Long postId) {
        return likeDAO.existsLikeByUserAndPost(userId, postId);
    }
}