package com.ess.essreddit.services;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.mapper.CommentMapper;
import com.ess.essreddit.model.User;
import com.ess.essreddit.repository.CommentRepository;
import com.ess.essreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentDto> getAllCommentsByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EssRedditException("No user found with username: " + username));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }
}
