package com.project.ets.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWT_Service {

    @Value(value ="myapp.jwt.secret" )
    private String secret;
}
