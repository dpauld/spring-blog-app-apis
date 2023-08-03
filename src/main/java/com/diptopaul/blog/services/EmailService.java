package com.diptopaul.blog.services;

import java.util.Locale;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;

public interface EmailService {
	public void sendPasswordResetEmail(UserDto userDto, PasswordResetTokenDto resetTokenDto, String appContextUrl, Locale locale);
}
