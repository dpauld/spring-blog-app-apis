package com.diptopaul.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exc) {
		//task: change ApiResponse class to fit this, then use it and remove map coding from here
		
		//create the response object 
		Map<String, String> resp = new HashMap<>();
		
		//since there can be more than one error, we need to loop through them.
		exc.getBindingResult().getAllErrors().forEach(err->{
			String fieldName = ((FieldError)err).getField();
			String message = err.getDefaultMessage();
			resp.put(fieldName, message);
		});
		
		//return the ResponseEntity; apiResponse represent T type that we defined during method definition ResponseEntity<ApiResponse>
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
	}
}
