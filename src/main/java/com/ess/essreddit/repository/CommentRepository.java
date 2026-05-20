package com.ess.essreddit.repository;

import com.ess.essreddit.model.Comment;
import com.ess.essreddit.model.Post;
import com.ess.essreddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);
    
    List<Comment> findAllByPost(Post post);
}
