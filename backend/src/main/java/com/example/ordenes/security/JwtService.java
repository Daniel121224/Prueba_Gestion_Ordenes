package com.example.ordenes.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {


    private final String SECRET =
            "mySecretKeyForJwtAuthenticationSpringBootProject123456";


    private final long EXPIRATION = 1000 * 60 * 60;


    public String generateToken(String username) {


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes(StandardCharsets.UTF_8)
                        )
                )
                .compact();
    }



    public String extractUsername(String token) {


        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes(StandardCharsets.UTF_8)
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }



    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    SECRET.getBytes(StandardCharsets.UTF_8)
                            )
                    )
                    .build()
                    .parseClaimsJws(token);


            return true;


        } catch (JwtException e) {

            return false;
        }
    }
}