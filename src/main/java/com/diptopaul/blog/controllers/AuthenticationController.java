package com.diptopaul.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diptopaul.blog.exceptions.ApiException;
import com.diptopaul.blog.exceptions.CustomBadCredentialsException;
import com.diptopaul.blog.payloads.AuthRequest;
import com.diptopaul.blog.payloads.JwtAuthResponse;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.security.JwtHelperService;
import com.diptopaul.blog.services.impl.AuthServiceImpl;
import com.diptopaul.blog.validationgroup.BaseValidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	private JwtHelperService jwtHelperService;
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	private AuthServiceImpl authServiceImpl;
	
	@Autowired
	public AuthenticationController(JwtHelperService jwtHelperService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, AuthServiceImpl authServiceImpl) {
		this.jwtHelperService = jwtHelperService;
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.authServiceImpl = authServiceImpl;
	}
	
	//for login, this returns the jwtToken, that will be saved somewhere in the clients browser
	//JwtAuthResponse in the return type is specificly for jwt based authentication return
	//Since regardless of auth type, all auth request will have username and password to map, AuthRequest in the parameter is specifically for all type of authentication, which is usefull to model map the User provided username and password inside the body
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody AuthRequest authRequest){
		//authenticate using authentication manager
		this.authenticate(authRequest.getUsername(), authRequest.getPassword());
		
		//create the jwt token, use username to get UserDetails and use the UD to get the token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
		
		String token = this.jwtHelperService.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		//this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		}catch (BadCredentialsException e) {
			throw new ApiException("Invalid username or password.");
		}
	}
	
	//for registration
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Validated(BaseValidation.class) @RequestBody UserDto userDto){
		UserDto registeredUserDto = this.authServiceImpl.registerUser(userDto);
		return new ResponseEntity<UserDto>(registeredUserDto, HttpStatus.CREATED);
	}
}
