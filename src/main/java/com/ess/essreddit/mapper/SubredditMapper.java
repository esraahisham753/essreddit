package com.ess.essreddit.mapper;

import com.ess.essreddit.dto.SubredditDto;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "id", source = "subredditId")
    @Mapping(target = "name", source = "subredditName")
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapToSubredditDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> subredditPosts) {
        return subredditPosts != null ? subredditPosts.size() : 0;
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Subreddit mapToSubreddit(SubredditDto subredditDto);
}
