package com.keresman.bookface.reply.controller;

import com.keresman.bookface.reply.dto.request.CreateReplyReq;
import com.keresman.bookface.reply.dto.request.UpdateReplyReq;
import com.keresman.bookface.reply.dto.response.ReplyDTO;
import com.keresman.bookface.reply.service.ReplyService;
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
@RequestMapping("/api/v1/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping
    public ResponseEntity<List<ReplyDTO>> getAllReplies() {
        return ResponseEntity
                .ok(replyService.getAllReplies());
    }

    @GetMapping("{replyId}")
    public ResponseEntity<ReplyDTO> getReply(@PathVariable Long replyId) {
        Optional<ReplyDTO> replyDTO = replyService.getReplyByIdOptional(replyId);

        return replyDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReplyDTO>> getRepliesByUser(@PathVariable Long userId) {
        return ResponseEntity
                .ok(replyService.getRepliesByUserId(userId));
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReplyDTO>> getRepliesByComment(@PathVariable Long commentId) {
        return ResponseEntity
                .ok(replyService.getRepliesByCommentId(commentId));
    }

    @PostMapping
    public ResponseEntity<?> createReply(@RequestBody CreateReplyReq createReplyReq) {
        Long replyId = replyService.createReply(createReplyReq);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(replyId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping("{replyId}")
    public ResponseEntity<?> updateReply(
            @PathVariable Long replyId,
            @RequestBody UpdateReplyReq updateReplyReq
    ) {
        replyService.updateReplyById(replyId, updateReplyReq);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReplyById(replyId);

        return ResponseEntity
                .noContent()
                .build();
    }
}