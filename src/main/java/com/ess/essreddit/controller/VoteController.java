package com.ess.essreddit.controller;

import com.ess.essreddit.dto.VoteDto;
import com.ess.essreddit.services.VoteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
@Tag(name = "Vote", description = "Vote management APIs")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteDto> makeVote(@RequestBody VoteDto voteDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voteService.saveVote(voteDto));
    }
}
