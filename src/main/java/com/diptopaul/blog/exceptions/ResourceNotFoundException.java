package com.diptopaul.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		// TODO Auto-generated constructor stub
		super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
		
		this.resourceName = resourceName;
		this.fieldName=fieldName;
		this.fieldValue=fieldValue;
	}
	public ResourceNotFoundException(String message) {
		// TODO Auto-generated constructor stub
		super(String.format("%s",message));
	}

}
