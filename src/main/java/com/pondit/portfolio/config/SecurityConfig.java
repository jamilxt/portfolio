package com.pondit.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/admin/login"));
        return http.build();
    }

    @Bean
    public SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/**").authenticated()
                )
                // TODO: apply JWT Token - Use Custom Filter
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
