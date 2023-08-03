package com.diptopaul.blog.services;

import com.diptopaul.blog.payloads.UserDto;

public interface AuthService {
	UserDto registerUser(UserDto userDto);
	void authenticate(String username, String password);
}
