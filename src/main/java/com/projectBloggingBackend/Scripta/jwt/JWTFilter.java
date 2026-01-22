package com.projectBloggingBackend.Scripta.jwt;

import com.projectBloggingBackend.Scripta.model.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class JWTFilter extends OncePerRequestFilter {
       private final JWTUtil jwtUtil;

       public JWTFilter(JWTUtil jwtUtil){
           this.jwtUtil=jwtUtil;
       }

       @Override
       protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException,IOException,java.io.IOException{

           if(SecurityContextHolder.getContext().getAuthentication()!=null){
               filterChain.doFilter(request,response);
               return;
           }

           String authHeader=request.getHeader("Authorization");

           if(authHeader!=null && authHeader.startsWith("Bearer ")) {
               String token = authHeader.substring(7);
               if (jwtUtil.validateToken(token)) {
                   String email = jwtUtil.ExtractEmail(token);
                   List<String> roles = jwtUtil.ExtractRoles(token);

                   System.out.println(roles+"🌟🌟🌟");
                   List<SimpleGrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role)).toList();
                   Authentication authentication = new UsernamePasswordAuthenticationToken(
                           email, null, authorities
                   );
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
           }
           filterChain.doFilter(request,response);
    }
}
