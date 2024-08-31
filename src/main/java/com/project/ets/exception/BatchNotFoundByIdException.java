package com.project.ets.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BatchNotFoundByIdException extends RuntimeException{
	private String message;
	@Override
	public String getMessage() {
		return message;
	}

}
