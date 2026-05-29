package com.ess.essreddit.services;

import com.ess.essreddit.model.User;
import com.ess.essreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public @NonNull UserDetails loadUserByUsername(@ NonNull String username) {
        Optional<User> returnedUser = userRepository.findUserByUsername(username);
        User user = returnedUser.orElseThrow(() -> new UsernameNotFoundException("Didn't find a user with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthority("USER")
        );
    }

    private Collection<? extends GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
