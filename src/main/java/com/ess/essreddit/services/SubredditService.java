package com.ess.essreddit.services;

import com.ess.essreddit.dto.SubredditDto;
import com.ess.essreddit.mapper.SubredditMapper;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto saveSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.mapToSubreddit(subredditDto);
        subredditRepository.save(subreddit);
        subredditDto.setId(subreddit.getSubredditId());

        return subredditDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapToSubredditDto)
                .collect(Collectors.toList());
    }
}
