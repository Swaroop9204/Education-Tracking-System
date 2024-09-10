package com.project.ets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class BeanConfig {

//    @Bean
    JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }
}
