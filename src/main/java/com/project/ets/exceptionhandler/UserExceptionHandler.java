package com.project.ets.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ets.exception.UserNotFoundByIdException;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class UserExceptionHandler {
	private AppResponseBuilder builder;
	
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(UserNotFoundByIdException exception){
		return builder.error(HttpStatus.NOT_FOUND,exception.getMessage(),"user not found by given id");
	}
	

}
