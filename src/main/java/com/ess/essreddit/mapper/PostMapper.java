package com.ess.essreddit.mapper;

import com.ess.essreddit.dto.PostRequest;
import com.ess.essreddit.dto.PostResponse;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.model.User;
import com.ess.essreddit.model.VoteType;
import com.ess.essreddit.repository.CommentRepository;
import com.ess.essreddit.repository.VoteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    public abstract Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "description", source = "post.description")
    @Mapping(target = "duration", expression = "java(getDuration(post.getCreatedDate()))")
    @Mapping(target = "username", expression = "java(post.getUser().getUsername())")
    @Mapping(target = "subredditName", expression = "java(post.getSubreddit().getSubredditName())")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(post))")
    @Mapping(target = "upvote", expression = "java(isUpvote(post, user))")
    @Mapping(target = "downvote", expression = "java(isDownvote(post, user))")
    public abstract PostResponse mapToPostResponse(Post post, User user);

    protected String getDuration(Instant createdDate) {
        PrettyTime prettyTime = new PrettyTime();

        return prettyTime.format(createdDate);
    }

    protected Integer getCommentCount(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    protected boolean isUpvote(Post post, User user) {
        return checkVoteType(post, user, VoteType.UPVOTE);
    }

    protected boolean isDownvote(Post post, User user) {
        return checkVoteType(post, user, VoteType.DOWNVOTE);
    }

    protected boolean checkVoteType(Post post, User user, VoteType voteType) {
        return voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user)
                .filter(vote -> vote.getVoteType().equals(voteType))
                .isPresent();
    }

}
