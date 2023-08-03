package com.diptopaul.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.Role;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ApiException;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.RoleRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
	
	private UserRepo userRepo;
	private ModelMapper modelMapper;
	private PasswordEncoder passwordEncoder;
	private RoleRepo roleRepo;
	private AuthenticationManager authenticationManager;
	

	@Override
	public void authenticate(String username, String password) {
		//this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		try {
			this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		}catch (BadCredentialsException e) {
			throw new ApiException("Invalid username or password.");
		}
	}
	
	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//encode the user provided password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//assign extra stuffs to the user that is not provided by the user, like: role(For normal user it should be ROLE_USER)
		//assign a role
		String roleName = "ROLE_USER";//roles are unique
		Role role = this.roleRepo.findByName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("Role","Name",roleName));
		//get the role Set and add a new role
		user.getRoles().add(role);
		
		//save the user when all fileds are ready
		User savedUser = this.userRepo.save(user);
		
		return this.modelMapper.map(savedUser,UserDto.class);
	}
	
}
