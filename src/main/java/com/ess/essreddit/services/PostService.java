package com.ess.essreddit.services;

import com.ess.essreddit.dto.PostRequest;
import com.ess.essreddit.dto.PostResponse;
import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.mapper.PostMapper;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.model.User;
import com.ess.essreddit.repository.PostRepository;
import com.ess.essreddit.repository.SubredditRepository;
import com.ess.essreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public PostResponse savePost(PostRequest postRequest) {
        Subreddit currentSubreddit = subredditRepository.findBySubredditName(postRequest.getSubredditName())
                .orElseThrow(() -> new EssRedditException("No subreddit found to add a post to"));

        User currentUser = authService.getCurrentUser();

        Post newPost =  postMapper.mapToPost(postRequest, currentSubreddit, currentUser);
        postRepository.save(newPost);

        return postMapper.mapToPostResponse(newPost, currentUser);
    }


    @Transactional(readOnly = true)
    public PostResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EssRedditException("No post found with id: " + postId));

        return postMapper.mapToPostResponse(post, authService.getCurrentUser());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> postMapper.mapToPostResponse(post, authService.getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new EssRedditException("No subreddit found with id: " + subredditId));

        return postRepository.findAllBySubreddit(subreddit)
                .stream()
                .map(post -> postMapper.mapToPostResponse(post, authService.getCurrentUser()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EssRedditException("No user found with username: " + username));

        return postRepository.findAllByUser(user)
                .stream()
                .map(post -> postMapper.mapToPostResponse(post, authService.getCurrentUser()))
                .collect(Collectors.toList());
    }
}
