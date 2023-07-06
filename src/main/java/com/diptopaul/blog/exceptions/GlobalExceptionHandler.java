package com.diptopaul.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.diptopaul.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value= {ResourceNotFoundException.class})
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exc) {
		//read the message from the error class
		String message = exc.getMessage();
		
		//create the response object 
		ApiResponse apiResponse = new ApiResponse(message, false);
		
		//return the ResponseEntity; apiResponse represent T type that we defined during method definition ResponseEntity<ApiResponse>
		return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
	}
}
