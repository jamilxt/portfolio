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
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private static final String[] ADMIN_PUBLIC_PAGES = {"/admin/login", "/admin/forget-password"};

    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(ADMIN_PUBLIC_PAGES).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/admin/login")
                );
        return http.build();
    }

    /**
     * Configures a security filter chain for REST API endpoints.
     *
     * This method sets up security rules for URLs matching the `/api/**` pattern.
     * It ensures that:
     * - The `/api/auth/login` endpoint is accessible without authentication.
     * - All other `/api/**` endpoints require authentication.
     *
     * Additionally, it:
     * - Disables CSRF protection as it is not needed for stateless APIs.
     * - Configures the session management policy to be stateless.
     * - Enables OAuth2 resource server support with JWT authentication.
     *
     * @param http the {@link HttpSecurity} object used to configure security settings
     * @return a {@link SecurityFilterChain} object representing the configured security filter chain
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    public SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(withDefaults());
                });
        return http.build();
    }

    /**
     * Creates a bean for password encoding using the BCrypt hashing algorithm.
     *
     * This method returns a {@link BCryptPasswordEncoder} instance with a specified strength.
     * The strength parameter determines the computational complexity of the hashing process.
     *
     * @return a {@link PasswordEncoder} instance configured with BCrypt hashing
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Configures an {@link AuthenticationManager} bean for managing authentication.
     *
     * This method sets up an {@link AuthenticationManager} using a {@link DaoAuthenticationProvider}.
     * The {@link DaoAuthenticationProvider} is configured with:
     * - A {@link UserDetailsService} to load user-specific data.
     * - A {@link PasswordEncoder} to handle password hashing and verification.
     *
     * @param authUserDetailsService the {@link UserDetailsService} used to retrieve user details
     * @param passwordEncoder the {@link PasswordEncoder} used for password encoding and verification
     * @return an {@link AuthenticationManager} instance configured with the specified authentication provider
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService authUserDetailsService,
                                             PasswordEncoder passwordEncoder) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
