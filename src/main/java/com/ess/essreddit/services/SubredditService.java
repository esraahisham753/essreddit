package com.ess.essreddit.services;

import com.ess.essreddit.dto.SubredditDto;
import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.mapper.SubredditMapper;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.model.User;
import com.ess.essreddit.repository.SubredditRepository;
import com.ess.essreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final UserRepository userRepository;

    @Transactional
    public SubredditDto saveSubreddit(SubredditDto subredditDto) {
        Subreddit subreddit = subredditMapper.mapToSubreddit(subredditDto);
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                                .getName();
        User currentUser = userRepository.findUserByUsername(username)
                        .orElseThrow(() -> new EssRedditException("No current user found with username: " + username));
        subreddit.setUser(currentUser);
        subreddit.setCreatedDate(Instant.now());
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

    @Transactional(readOnly = true)
    public SubredditDto getSubredditById(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new EssRedditException("Cannot find subreddit with id " + id));

        return subredditMapper.mapToSubredditDto(subreddit);
    }
}
