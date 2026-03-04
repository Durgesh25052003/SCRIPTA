package com.projectBloggingBackend.Scripta.security;

import com.projectBloggingBackend.Scripta.jwt.JWTFilter;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JWTFilter jwtFilter;
    private final RateLimiter rateLimiter;

    public SecurityConfig(JWTFilter jwtFilter,RateLimiter rateLimiter){
        this.jwtFilter=jwtFilter;
        this.rateLimiter=rateLimiter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/scripta/auth/**").permitAll()
                .requestMatchers("/api/v1/scripta/test/**").hasAnyRole("ADMIN","USER").requestMatchers("/api/v1/scripta/post").hasAnyRole("ADMIN","USER").requestMatchers("/api/v1/scripta/comment").hasAnyRole("ADMIN","USER").requestMatchers("/api/v1/scripta/like").hasAnyRole("USER","ADMIN").requestMatchers("/api/v1/scripta/feed").hasAnyRole("ADMIN","USER").requestMatchers("/api/v1/scripta/like").hasAnyRole("USER","ADMIN").anyRequest().authenticated()
        ).addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        ).addFilterAfter(
                rateLimiter,JWTFilter.class
        );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
    }

}
