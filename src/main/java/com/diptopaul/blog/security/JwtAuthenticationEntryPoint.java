package com.diptopaul.blog.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.diptopaul.blog.payloads.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//The AccessDeniedHandler only applies to authenticated users. For all the other anonymous unathenticated user write code/responses here.
		
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied!! Please Login.");//does not show the output message
		
		ApiResponse jsonResponse = new ApiResponse("Access Denied!! Please Login.", false);
		
        // Convert the JSON response to a string
        String responseBody = objectMapper.writeValueAsString(jsonResponse);

        // Set the response status and write the JSON response to the output stream
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(responseBody);
        
	    // Recommended Approach: Redirect unauthenticated users to the login page
	    //response.sendRedirect("/login");
	}

}
