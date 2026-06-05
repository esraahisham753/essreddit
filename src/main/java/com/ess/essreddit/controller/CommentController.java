package com.ess.essreddit.controller;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
@Tag(name = "Comment", description = "Comments management APIs")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Create a new comment on a post")
    @ApiResponse(responseCode = "201", description = "Comment created successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.saveComment(commentDto));
    }
}