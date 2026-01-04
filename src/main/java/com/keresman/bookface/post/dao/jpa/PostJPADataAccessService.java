package com.keresman.bookface.post.dao.jpa;

import com.keresman.bookface.post.dao.PostDAO;
import com.keresman.bookface.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postJpa")
@RequiredArgsConstructor
public class PostJPADataAccessService implements PostDAO {

    private static final int DEFAULT_PAGE_SIZE = 1_000;

    private final PostRepository postRepository;

    @Override
    public List<Post> selectAllPosts() {
        Page<Post> postsPage = postRepository.findAll(Pageable.ofSize(DEFAULT_PAGE_SIZE));
        return postsPage.getContent();
    }

    @Override
    public Optional<Post> selectPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> selectPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public void insertPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public boolean existsPostWithId(Long id) {
        return postRepository.existsById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }
}
