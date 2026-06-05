package com.ess.essreddit.controller;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "User management APIs")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Retrieve all comments made by a specific user")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{username}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllCommentsByUsername(username));
    }
}