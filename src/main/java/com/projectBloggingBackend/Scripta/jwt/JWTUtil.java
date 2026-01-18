package com.projectBloggingBackend.Scripta.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private long expiry;

   private Key getKey(){
       return Keys.hmacShaKeyFor(secretKey.getBytes());
   }

   public Claims extractAllClaims(String token){
       return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
   }

   public String createToken(String email, Map<String, Object> claims){
     return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+ expiry)).signWith(getKey(),SignatureAlgorithm.HS256).compact();
   }

   public Boolean validateToken(String token){
       try{
           Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
           return true;
       } catch (RuntimeException e) {
           throw new RuntimeException(e);
       }
   }

    //Extract Email
    public String ExtractEmail(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public List ExtractRoles(String token){
        return extractAllClaims(token).get("roles",List.class);
    }
}
