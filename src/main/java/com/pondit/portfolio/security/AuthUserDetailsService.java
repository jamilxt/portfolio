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

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads a user's details by their username.
     *
     * This method retrieves a {@link UserEntity} from the database using the provided username.
     * If the username is not found, a {@link UsernameNotFoundException} is thrown.
     * The retrieved {@link UserEntity} is then converted into an {@link AuthUser} object,
     * which implements {@link UserDetails}.
     *
     * @param username the username of the user to be loaded
     * @return a {@link UserDetails} object containing the user's details
     * @throws UsernameNotFoundException if no user is found with the given username
     */
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
