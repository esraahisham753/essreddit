package com.ess.essreddit.mapper;

import com.ess.essreddit.dto.PostRequest;
import com.ess.essreddit.dto.PostResponse;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.model.User;
import com.ess.essreddit.repository.CommentRepository;
import com.ess.essreddit.repository.VoteRepository;
import com.ess.essreddit.services.AuthService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@AllArgsConstructor
@Mapper(componentModel = "spring")
public abstract class PostMapper {
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;

    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    public abstract Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user);

    /* @Mapping(target = "description", source = "post.description")
    public abstract PostResponse mapToPostResponse(Post post, Subreddit subreddit, User user);

    private String getDuration(Instant createdDate) {

    } */

}
