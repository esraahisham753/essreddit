package com.ess.essreddit.services;

import com.ess.essreddit.dto.VoteDto;
import com.ess.essreddit.exceptions.PostNotFoundException;
import com.ess.essreddit.mapper.VoteMapper;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.User;
import com.ess.essreddit.model.Vote;
import com.ess.essreddit.model.VoteType;
import com.ess.essreddit.repository.PostRepository;
import com.ess.essreddit.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final AuthService authService;
    private final PostRepository postRepository;

    public VoteDto saveVote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("No post found with id: " + voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();
        // Check if there is a vote already
        Optional<Vote> currentVote = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        if (currentVote.isPresent()) {
            Vote currentVoteValue = currentVote.get();

            if (currentVoteValue.getVoteType().equals(voteDto.getVoteType())) {
                voteRepository.delete(currentVoteValue);

                if (voteDto.getVoteType().equals(VoteType.UPVOTE)) {
                    post.setVoteCount(post.getVoteCount() - 1);
                } else {
                    post.setVoteCount(post.getVoteCount() + 1);
                }
            } else if (voteDto.getVoteType().equals(VoteType.UPVOTE)) {
                currentVoteValue.setVoteType(VoteType.UPVOTE);
                post.setVoteCount(post.getVoteCount() + 2);
                voteRepository.save(currentVoteValue);
                postRepository.save(post);
            } else {
                currentVoteValue.setVoteType(VoteType.DOWNVOTE);
                post.setVoteCount(post.getVoteCount() - 2);
                voteRepository.save(currentVoteValue);
                postRepository.save(post);
            }
        } else {
            Vote newVote = voteMapper.mapToVote(voteDto, post, currentUser);
            voteRepository.save(newVote);

            if (voteDto.getVoteType().equals(VoteType.UPVOTE)) {
                post.setVoteCount(post.getVoteCount() + 1);
            } else {
                post.setVoteCount(post.getVoteCount() - 1);
            }

            postRepository.save(post);
        }

        return voteDto;
    }
}
