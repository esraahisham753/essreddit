package com.ess.essreddit.repository;

import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.Subreddit;
import com.ess.essreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
