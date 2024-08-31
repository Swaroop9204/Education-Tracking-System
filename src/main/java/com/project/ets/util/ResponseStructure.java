package com.project.ets.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseStructure<T> {

	private int status;
	private String message;
	private T data;
	
	
	public static <T>ResponseStructure<T> create(int status,String message,T data){
		ResponseStructure<T> responseStructure=new ResponseStructure<>();
		responseStructure.setStatus(status);
		responseStructure.setMessage(message);
		responseStructure.setData(data);
		return responseStructure;
	}
}
