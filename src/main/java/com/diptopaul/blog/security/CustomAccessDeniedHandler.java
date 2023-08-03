package com.diptopaul.blog.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.diptopaul.blog.payloads.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		 // Create a JSON response
//        Map<String, String> jsonResponse = new HashMap<>();
//        jsonResponse.put("success", "false");
//        jsonResponse.put("message", "You don't have permission to access this resource.");
		
		ApiResponse jsonResponse = new ApiResponse("You don't have permission to access this resource.", false);
		
        // Convert the JSON response to a string
        String responseBody = objectMapper.writeValueAsString(jsonResponse);

        // Set the response status and write the JSON response to the output stream
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(responseBody);
	}
	
}
