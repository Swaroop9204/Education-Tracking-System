package com.project.ets.util;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AppResponseBuilder {
	public <T> ResponseEntity<ResponseStructure<T>> success(HttpStatus status,String message,T data){
		return ResponseEntity.status(status).body(ResponseStructure.create(status.value(), message, data));
	}
	
	public ResponseEntity<Object> fieldErrors(HttpStatus status, String message, List<CustomFieldError> errors){
		return ResponseEntity.status(status).body(ErrorStructure.error(status.value(), message, errors));
	}

	public ResponseEntity<ErrorStructure<String>> error(HttpStatus notFound, String message, String string) {
		return ResponseEntity.status(notFound).body(ErrorStructure.error(notFound.value(), message, string));
	}

}
