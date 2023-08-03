package com.diptopaul.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private String field;
	private boolean sucess;
	
	
	public ApiResponse(String message) {
		this.message = message;
	}

	public ApiResponse(String message, String field) {
		this.message = message;
		this.message = field;
	}
	
	public ApiResponse(String message, boolean sucess) {
		super();
		this.message = message;
		this.sucess = sucess;
	}

	//since java does not allow similar constructor used a dummy
	public ApiResponse(String field, boolean sucess, boolean dummy) {
		this.field = field;
		this.sucess = sucess;
	}
	
	
}
