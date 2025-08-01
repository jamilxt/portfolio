package com.pondit.portfolio.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration

class SecurityConfig {

        @Bean
        public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/admin/**")
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/admin/**").authenticated()
                            .anyRequest().permitAll()
                    )
                    .httpBasic(withDefaults());
            return http.build();
        }

        @Bean
        public DefaultSecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/api/**")
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/api/auth/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    // TODO: apply JWT Token - Use Custom Filter
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .oauth2ResourceServer(oauth2 -> {
                        oauth2.jwt(withDefaults());
                    });
            return http.build();
        }

            @Bean
            public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
            }

            @Bean
            public AuthenticationManager authManager(UserDetailsService authUserDetailsService,
                                                     PasswordEncoder passwordEncoder) {
                var authProvider = new DaoAuthenticationProvider();
                authProvider.setUserDetailsService(authUserDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder);
                return new ProviderManager(authProvider);
            }
    }



