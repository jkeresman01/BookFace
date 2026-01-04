package com.keresman.bookface.post.controller;

import com.keresman.bookface.post.dto.request.CreatePostReq;
import com.keresman.bookface.post.dto.request.UpdatePostReq;
import com.keresman.bookface.post.dto.response.PostDTO;
import com.keresman.bookface.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity
                .ok(postService.getAllPosts());
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long postId) {
        Optional<PostDTO> postDTO = postService.getPostByIdOptional(postId);

        return postDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity
                .ok(postService.getPostsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostReq createPostReq) {
        Long postId = postService.createPost(createPostReq);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping("{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostReq updatePostReq
    ) {
        postService.updatePostById(postId, updatePostReq);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePostById(postId);

        return ResponseEntity
                .noContent()
                .build();
    }
}