package com.project.ets.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidOtpException extends RuntimeException{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
