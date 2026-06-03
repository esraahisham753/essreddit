package com.ess.essreddit.mapper;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.model.Comment;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Comment mapToComment(CommentDto commentDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToCommentDto(Comment comment);
}
