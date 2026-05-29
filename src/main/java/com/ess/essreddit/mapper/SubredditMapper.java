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
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapToSubredditDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> subredditPosts) {
        return subredditPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "numberOfPosts", ignore = true)
    Subreddit mapToSubreddit(SubredditDto subredditDto);
}
