package com.diptopaul.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	
	private String resourceName;
	private String fieldName;
	private Integer fieldValue;

	
	public ResourceNotFoundException(String resourceName, String fieldName, Integer fieldValue) {
		// TODO Auto-generated constructor stub
		super(String.format("%s is not found with %s : %s",resourceName,fieldName,fieldValue));
		
		this.resourceName = resourceName;
		this.fieldName=fieldName;
		this.fieldValue=fieldValue;
	}

}
