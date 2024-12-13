package com.shaylawhite.gems_of_life.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Disable CSRF for APIs
                .authorizeRequests(authz -> authz
                        .requestMatchers("/game/start").permitAll()  // Allow anyone to access the start endpoint
                        .requestMatchers("/game/guess/**").permitAll()  // Allow anyone to access the guess endpoint
                        .anyRequest().authenticated()  // Other requests require authentication
                )
                .httpBasic().disable()  // Disable HTTP Basic authentication if not needed
                .formLogin().disable();  // Disable form login as it's unnecessary for APIs

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.builder()
                .username("admin")  // Admin user for testing
                .password(passwordEncoder().encode("admin123"))  // Encoded password
                .roles("USER")  // Role for the user
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password encoding
    }
}


