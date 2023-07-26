package com.diptopaul.blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final JwtHelperService jwtHelperService;
	private final CustomUserDetailsService customUserDetailsService;
	
	
	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,@Nonnull HttpServletResponse response,@Nonnull FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		String userEmail=null;
		if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//get the token
		jwt = authHeader.substring(7);
		
		//now use this token to do the rest of the work
		try{
			userEmail = jwtHelperService.extractUsername(jwt);//todo extract the userEmail from JWT token
		}catch(IllegalArgumentException e){
			System.out.println("Unable to get Jwt token");
		}catch(ExpiredJwtException e){
			System.out.println("Jwt token has expired");
		}catch(MalformedJwtException e){
			System.out.println("Invalid JWT");
		}
		
		//Once we get the token, time to validate it
		//if userEmail fetched from token is not null and user is not being authenticated yet which we can check by getting the authentication and checking if it is empty or null
		if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//then addd the new user inside the SecurityContext
			
			//first use the userEmail to get the userDetails from the method loadUserByUsername provided by CustomUserDetailsService
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userEmail);
			
			//check if the token is valid for this userDetails
			if(this.jwtHelperService.isTokenValid(jwt, userDetails)) {
				//So the token is valid for this user, then we can use the information we got from token here
				
				//The second parameter is the credentials (usually the password), with JWT-based authentication we don't have a password, so we pass null
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, null, userDetails.getAuthorities());
				//add extra details
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//now add the new UsernamePasswordAuthenticationToken object authToken inside the context of SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
