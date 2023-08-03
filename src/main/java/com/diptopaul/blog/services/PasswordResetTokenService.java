package com.diptopaul.blog.services;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;

public interface PasswordResetTokenService {
	public PasswordResetTokenDto createToken(UserDto user);
	public boolean validateToken(String token);
	public void resetPassword(String token, String newPassword);
}
