package com.ess.essreddit.services;

import com.ess.essreddit.dto.CommentDto;
import com.ess.essreddit.exceptions.PostNotFoundException;
import com.ess.essreddit.mapper.CommentMapper;
import com.ess.essreddit.model.Comment;
import com.ess.essreddit.model.NotificationEmail;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.repository.CommentRepository;
import com.ess.essreddit.repository.PostRepository;
import com.ess.essreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final EmailBuilderService emailBuilderService;
    private final EmailSenderService emailSenderService;

    public CommentDto saveComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found with id: " + commentDto.getPostId()));

        Comment newComment = commentMapper.mapToComment(commentDto, post, authService.getCurrentUser());
        commentRepository.save(newComment);

        sendNotificationEmail("The user " + authService.getCurrentUser().getUsername() + "commented on your post: " + post.getUrl());

        commentDto.setId(newComment.getId());

        return commentDto;
    }

    private void sendNotificationEmail(String message) {
        String email = emailBuilderService.build(message);
        emailSenderService.sendEmail(new NotificationEmail(
                "New comment on your post",
                authService.getCurrentUser().getEmail(),
                message
        ));
    }
}
