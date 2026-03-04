package com.projectBloggingBackend.Scripta.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimiter extends OncePerRequestFilter {
    private StringRedisTemplate redisTemplate;

    private static final int REQUEST_LIMIT = 4;
    private static final int WINDOW_SECONDS = 60;

    public RateLimiter(StringRedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
    }

    @Override
    protected  void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || !authentication.isAuthenticated()){
            filterChain.doFilter(request,response);
            return;
        }

        String getName=authentication.getName();
        String key= "rate_Limiter:user:"+getName;
        try{
            Long Count=redisTemplate.opsForValue().increment(key);
            if(Count!=null && Count==1){
                redisTemplate.expire(key, Duration.ofSeconds(WINDOW_SECONDS));
            }
            if(Count !=null && Count>REQUEST_LIMIT){
                response.setStatus(429);
                response.getWriter().write("Too Many Requests...");
                return;
            }
        }catch(RuntimeException e){
            filterChain.doFilter(request,response);
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);
    }
}
