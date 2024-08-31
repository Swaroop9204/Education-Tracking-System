package com.project.ets.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.ets.exception.RatingNotFoundByIdException;
import com.project.ets.util.AppResponseBuilder;
import com.project.ets.util.ErrorStructure;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class RatingExceptionHandler {
	private AppResponseBuilder appResponseBuilder;
	
	
	@ExceptionHandler(RatingNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure<String>> handleRatingNotFoundById(RatingNotFoundByIdException exception){
		return appResponseBuilder.error(HttpStatus.NOT_FOUND, exception.getMessage(),"rating not found by given id");
	}
	

}
