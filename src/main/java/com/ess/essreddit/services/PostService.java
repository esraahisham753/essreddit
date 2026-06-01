package com.ess.essreddit.services;

import com.ess.essreddit.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;


}
