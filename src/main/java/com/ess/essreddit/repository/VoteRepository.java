package com.ess.essreddit.repository;

import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.User;
import com.ess.essreddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostUserOrderByVoteIdDesc(User user, Post post);
}
