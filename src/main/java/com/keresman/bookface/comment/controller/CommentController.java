package com.keresman.bookface.comment.controller;

import com.keresman.bookface.comment.dto.request.CreateCommentReq;
import com.keresman.bookface.comment.dto.request.UpdateCommentReq;
import com.keresman.bookface.comment.dto.response.CommentDTO;
import com.keresman.bookface.comment.service.CommentService;
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
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity
                .ok(commentService.getAllComments());
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long commentId) {
        Optional<CommentDTO> commentDTO = commentService.getCommentByIdOptional(commentId);

        return commentDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUser(@PathVariable Long userId) {
        return ResponseEntity
                .ok(commentService.getCommentsByUserId(userId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity
                .ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CreateCommentReq createCommentReq) {
        Long commentId = commentService.createComment(createCommentReq);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commentId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping("{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentReq updateCommentReq
    ) {
        commentService.updateCommentById(commentId, updateCommentReq);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteCommentById(commentId);

        return ResponseEntity
                .noContent()
                .build();
    }
}