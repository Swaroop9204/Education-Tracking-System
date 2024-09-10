package com.project.ets.exceptionhandler;

import com.project.ets.exception.InvalidOtpException;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ErrorStructure;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class InvalidOtpExceptionHandler {
    private AppResponseBuilder responseBuilder;

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorStructure<String>> handleInavlidOtpException(InvalidOtpException exception){
        return responseBuilder.error(HttpStatus.NOT_FOUND,exception.getMessage(),"Invalid Otp or expired");
    }
}
