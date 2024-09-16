package com.project.ets.security;

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
public class JWT_Service {

    @Value("${myapp.jwt.secret}" )
    private String secret;
    @Value("${myapp.jwt.access_expiry}")
    private long access_expiry;


    public String createJwt(String userId,String email,String role){
       return Jwts.builder()
                .setClaims(Map.of("userId",userId,"email",email,"role",role))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+access_expiry*60*1000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey(){
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
