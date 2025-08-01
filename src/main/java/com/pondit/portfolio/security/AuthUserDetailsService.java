package com.pondit.portfolio.security;

import com.pondit.portfolio.persistence.entity.UserEntity;
import com.pondit.portfolio.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<UserEntity> userEntityOptional = userRepository.findByUsername(username);
            userEntityOptional.orElseThrow(() -> {
                log.debug("username not found {}", username);
                return new UsernameNotFoundException("User not found with username: " + username);
            });
            UserEntity userEntity = userEntityOptional.get();
            return new AuthUser(userEntity);
        }
    }

