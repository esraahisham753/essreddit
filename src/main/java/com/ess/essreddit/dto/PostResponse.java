package com.ess.essreddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String postName;
    private String url;
    private String description;
    private Integer voteCount;
    private String duration;
    private String username;
    private String subredditName;
    private Integer commentCount;
    private boolean upvote;
    private boolean downvote;
}
