package com.diptopaul.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diptopaul.blog.payloads.ApiResponse;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.services.UserService;
import com.diptopaul.blog.validationgroup.UpdateValidation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//POST-create user
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
	}
	
	//PUT-update user
//	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @customPermissionEvaluator.hasPermission(#uid, 'User', 'edit'))")//only admin and the designated user can update a user
//	@PutMapping("/users/{userId}")
//	//public ResponseEntity<UserDto> udpateUser(@RequestBody UserDto userDto, @PathVariable Integer userId){
//	public ResponseEntity<UserDto> udpateUser(@Validated @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
//		return ResponseEntity.ok(updatedUserDto);
//	}
	
	//PUT-update user
	@PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and @customPermissionEvaluator.hasPermission(#uid, 'User', 'edit'))")//only admin and the designated user can update a user
	@PutMapping("/users/{userId}")
	//public ResponseEntity<UserDto> udpateUser(@RequestBody UserDto userDto, @PathVariable Integer userId){
	public ResponseEntity<UserDto> udpateUser(@Validated(UpdateValidation.class) @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		System.out.println(userDto.toString());
		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUserDto);
	}
	
	//DELETE-delete user
	@PreAuthorize("hasRole('ADMIN')")//only admin can delete a user
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId);
		//return ResponseEntity.ok(Map.of("message","User deleted successfully"));
		return new ResponseEntity<>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
	}
	

	//GET-get all user
	@PreAuthorize("hasRole('ADMIN')")//only admin can get the list of all users
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> userDtos = this.userService.getAllUsers();
		return ResponseEntity.ok(userDtos);
	}
	
	//GET-get single user
	//anyone can acess single user info, will make it limited info for outsider to view
	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		UserDto userDto = this.userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}
}
