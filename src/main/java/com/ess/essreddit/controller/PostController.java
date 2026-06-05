package com.ess.essreddit.controller;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.dto.PostRequest;
import com.ess.essreddit.dto.PostResponse;
import com.ess.essreddit.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
@Tag(name = "Post", description = "Post management APIs")
public class PostController {
    private final PostService postService;

    @Operation(summary = "Create a new post in a subreddit")
    @ApiResponse(responseCode = "201", description = "Post created successfully")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.savePost(postRequest));
    }

    @Operation(summary = "Retrieve all posts")
    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getAllPosts());
    }

    @Operation(summary = "Retrieve all comments for a post by its ID")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getCommentsByPostId(id));
    }

    @Operation(summary = "Retrieve a single post by its ID")
    @ApiResponse(responseCode = "200", description = "Post retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostById(id));
    }

    @Operation(summary = "Retrieve all posts belonging to a subreddit")
    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Subreddit not found")
    @GetMapping(params = "subredditId")
    public ResponseEntity<List<PostResponse>> getPostsBySubredditId(@RequestParam Long subredditId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostsBySubreddit(subredditId));
    }

    @Operation(summary = "Retrieve all posts made by a specific user")
    @ApiResponse(responseCode = "200", description = "Posts retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping(params = "username")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@RequestParam String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostsByUsername(username));
    }
}