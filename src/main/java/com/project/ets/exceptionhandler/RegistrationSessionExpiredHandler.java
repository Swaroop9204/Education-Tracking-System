package com.project.ets.exceptionhandler;

import com.project.ets.exception.RegistrationSessionExpiredexception;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ErrorStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AllArgsConstructor
@RestControllerAdvice
public class RegistrationSessionExpiredHandler {
    private final AppResponseBuilder responseBuilder;

    @ExceptionHandler(RegistrationSessionExpiredexception.class)
    public ResponseEntity<ErrorStructure<String>> handleRegistration(RegistrationSessionExpiredexception exception){
        return  responseBuilder.error(HttpStatus.NOT_FOUND,exception.getMessage(),"failed to register session is expired");
    }
}
