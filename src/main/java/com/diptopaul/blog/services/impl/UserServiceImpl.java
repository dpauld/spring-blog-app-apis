package com.diptopaul.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	//try task:
	//make createUser and updateUser to one method to save as like Chad Derby did
	//@Autowired
	private UserRepo userRepo;
	
	//@Autowired
	private ModelMapper modelMapper;
	
	//Construct injection is recommended over field injection
	//As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary if the target bean only defines one constructor to begin with
	//@Autowired
	public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper) {
		this.userRepo=userRepo;
		this.modelMapper=modelMapper;
	}
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		//to convert user to userDto we usually use model mapper library, 
		//for now we will use a custom method for this job
		User user = this.dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		
		return this.userToDto(savedUser);
	} 

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
	
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		//convert list of users to list of userDtos using stream api 
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		
		this.userRepo.delete(user);
	}
	
	//helper methods, without using modelMapper
//	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
//		
//	}
//
//	private UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		return userDto;
//	}
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
		
	}

	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user,UserDto.class);
		return userDto;
	}
}
