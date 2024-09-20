package com.project.ets.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${my_app.jwt.secret}" )
    private String secret;

    @Value("${my_app.jwt.access_expiry}")
    private long accessExpiry;

    @Value("${my_app.jwt.refresh_expiry}")
    private long refreshExpiry;

    public String generateAccessToken(String userId,String email,String role){
        return createJwt(userId,email,role,accessExpiry);
    }

    public String generateRefreshToken(String userId,String email,String role){
        return createJwt(userId,email,role,refreshExpiry);
    }

    private String createJwt(String userId,String email,String role,long expiry){
       return Jwts.builder()
                .setClaims(Map.of("userId",userId,"email",email,"role",role))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiry*60*1000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public Claims parseJwt(String token){
        JwtParser jwtParser=Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
