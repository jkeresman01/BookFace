package com.keresman.bookface.like.controller;

import com.keresman.bookface.like.dto.request.CreateLikeReq;
import com.keresman.bookface.like.dto.response.LikeDTO;
import com.keresman.bookface.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<LikeDTO>> getAllLikes() {
        return ResponseEntity
                .ok(likeService.getAllLikes());
    }

    @GetMapping("{likeId}")
    public ResponseEntity<LikeDTO> getLike(@PathVariable Long likeId) {
        Optional<LikeDTO> likeDTO = likeService.getLikeByIdOptional(likeId);

        return likeDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDTO>> getLikesByUser(@PathVariable Long userId) {
        return ResponseEntity
                .ok(likeService.getLikesByUserId(userId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDTO>> getLikesByPost(@PathVariable Long postId) {
        return ResponseEntity
                .ok(likeService.getLikesByPostId(postId));
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Integer> getLikesCountByPost(@PathVariable Long postId) {
        return ResponseEntity
                .ok(likeService.getLikesCountByPostId(postId));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserLikedPost(
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        return ResponseEntity
                .ok(likeService.hasUserLikedPost(userId, postId));
    }

    @PostMapping
    public ResponseEntity<?> createLike(@RequestBody CreateLikeReq createLikeReq) {
        Long likeId = likeService.createLike(createLikeReq);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(likeId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggleLike(@RequestBody CreateLikeReq createLikeReq) {
        likeService.toggleLike(createLikeReq.userId(), createLikeReq.postId());

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("{likeId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long likeId) {
        likeService.deleteLikeById(likeId);

        return ResponseEntity
                .noContent()
                .build();
    }
}