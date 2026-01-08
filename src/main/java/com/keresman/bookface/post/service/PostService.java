package com.keresman.bookface.post.service;

import com.keresman.bookface.post.dao.PostDAO;
import com.keresman.bookface.post.dto.request.CreatePostReq;
import com.keresman.bookface.post.dto.request.UpdatePostReq;
import com.keresman.bookface.post.dto.response.PostDTO;
import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.post.mapping.dto.PostDTOMapper;
import com.keresman.bookface.user.dao.UserDAO;
import com.keresman.bookface.user.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostDAO postDAO;
    private final UserDAO userDAO;
    private final PostDTOMapper postDTOMapper;

    public PostService(
            @Qualifier("postJpa") PostDAO postDAO,
            @Qualifier("jpaUser") UserDAO userDAO,
            PostDTOMapper postDTOMapper
    ) {
        this.postDAO = postDAO;
        this.userDAO = userDAO;
        this.postDTOMapper = postDTOMapper;
    }

    public List<PostDTO> getAllPosts() {
        return postDAO
                .selectAllPosts().stream()
                .map(postDTOMapper)
                .toList();
    }

    public Optional<PostDTO> getPostByIdOptional(Long postId) {
        return postDAO
                .selectPostById(postId)
                .map(postDTOMapper);
    }

    public List<PostDTO> getPostsByUserId(Long userId) {
        return postDAO.selectPostsByUserId(userId).stream()
                .map(postDTOMapper)
                .toList();
    }

    public Long createPost(CreatePostReq createPostReq) {
        User user = userDAO
                .selectUserById(createPostReq.userId())
                .orElseThrow(() ->
                        new RuntimeException("User with id [%d] not found".formatted(createPostReq.userId())));

        Post post = new Post(
                null,  // id will be auto-generated
                createPostReq.imageId(),
                createPostReq.title(),
                createPostReq.content(),
                user
        );

        postDAO.insertPost(post);

        return post.getId();
    }

    public void updatePostById(Long postId, UpdatePostReq updatePostReq) {
        Post post = postDAO
                .selectPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post with id [%d] not found".formatted(postId)));

        boolean changes = false;

        if (updatePostReq.title() != null && !updatePostReq.title().equals(post.getTitle())) {
            post.setTitle(updatePostReq.title());
            changes = true;
        }

        if (updatePostReq.content() != null && !updatePostReq.content().equals(post.getContent())) {
            post.setContent(updatePostReq.content());
            changes = true;
        }

        if (updatePostReq.imageId() != null && !updatePostReq.imageId().equals(post.getImageId())) {
            post.setImageId(updatePostReq.imageId());
            changes = true;
        }

        if (!changes) {
            return;
        }

        postDAO.updatePost(post);
    }

    public void deletePostById(Long postId) {
        if (!postDAO.existsPostWithId(postId)) {
            throw new RuntimeException("Post with id [%d] not found".formatted(postId));
        }

        postDAO.deletePostById(postId);
    }
}