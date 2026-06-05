package com.ess.essreddit.controller;

import com.ess.essreddit.dto.SubredditDto;
import com.ess.essreddit.services.SubredditService;
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
@RequestMapping("/api/subreddits")
@AllArgsConstructor
@Slf4j
@Tag(name = "Subreddit", description = "Subreddit management APIs")
public class SubredditController {
    private final SubredditService subredditService;

    @Operation(summary = "Create a new subreddit")
    @ApiResponse(responseCode = "201", description = "Subreddit created successfully")
    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.saveSubreddit(subredditDto));
    }

    @Operation(summary = "Retrieve all subreddits")
    @ApiResponse(responseCode = "200", description = "Subreddits retrieved successfully")
    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @Operation(summary = "Retrieve a subreddit by its ID")
    @ApiResponse(responseCode = "200", description = "Subreddit retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Subreddit not found")
    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getSubredditById(id));
    }
}