package com.ess.essreddit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    private long id;

    @NotBlank(message = "Community name cannot be empty")
    private String name;

    @NotBlank(message = "Community description cannot be empty")
    private String description;

    private Integer numberOfPosts;
}
