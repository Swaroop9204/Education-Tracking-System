package com.project.ets.util;

import com.project.ets.entity.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheHelper {

    @CachePut(cacheNames = "nonverifieduser", key = "#user.email")
    public User cacheUser(User user){
        return user;
    }
    @Cacheable(cacheNames = "otps" ,key = "#otp")
    public int cacheOtp(int otp){
        return otp;
    }
}
