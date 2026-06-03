package com.ess.essreddit.mapper;

import com.ess.essreddit.dto.VoteDto;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.User;
import com.ess.essreddit.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoteMapper {
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote mapToVote(VoteDto voteDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(vote.getPost().getPostId())")
    VoteDto mapToVoteDto(Vote vote);
}
