package com.studyplanner.authservice.securityUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component

public class JwtUtil {

    // secret key - from properties
    @Value("${jwt.secret}")
    private String secreteKey;

    // expiration -from properties
    @Value("${jwt.expiration}")
    private long expiration;

    //Generate token
    public String generateToken(String username){
            return Jwts.builder()
                    .setClaims( new HashMap<>())
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS256, secreteKey)
                    .compact();
        }

       /* public Boolean validateToken(String token, String userDetails) {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } */


}



