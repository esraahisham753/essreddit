package com.ess.essreddit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;

    @NotBlank(message = "Post name cannot be blank")
    private String postName;

    private String subredditName;
    private String description;
    private String url;
}
