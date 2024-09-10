package com.project.ets.util;

import com.project.ets.entity.User;
import com.project.ets.exception.InvalidOtpException;
import com.project.ets.exception.RegistrationSessionExpiredexception;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CacheHelper  {

    @CachePut(cacheNames = "nonverifieduser", key = "#user.email")
    public User cacheUser(User user){
        return user;
    }

    @Cacheable(cacheNames = "otps" ,key = "#email")
    public int cacheOtp(int otp,String email){
        return otp;
    }

    @Cacheable(cacheNames = "nonverifieduser",key = "#email")
    public User getRegisterUser(String email){
        return new User();
    }

    @Cacheable(cacheNames = "otps",key = "#email")
    public Integer getOtp(String email){
        return 0;
    }
}
