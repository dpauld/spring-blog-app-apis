package com.diptopaul.blog.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diptopaul.blog.repositories.RoleRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.UserService;

//@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepo userRepo;
	@Mock
	private RoleRepo roleRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	AutoCloseable autoCloseable;
	UserService userService;
	
	@BeforeEach
	void setUp() throws Exception {
		autoCloseable = MockitoAnnotations.openMocks(this);
		this.userService = new UserServiceImpl(this.userRepo, this.modelMapper, this.passwordEncoder, this.roleRepo);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.autoCloseable.close();
	}

	@Test
	void testGetUserById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserByEmail() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllUser() {
		System.out.println(userService.getAllUsers());
//		userService.getAllUsers();
		verify(userRepo).findAll();
	}
}
