package com.diptopaul.blog.services.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.PasswordResetToken;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.PasswordResetTokenRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.PasswordResetTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{
	
	private static final int EXPIRATION_TIME_MIN = 5;
	
	private PasswordResetTokenRepo passwordResetTokenRepo;
	private ModelMapper modelMapper;
	private UserRepo userRepo;
	private PasswordEncoder passwordEncoder;

	@Override
	public PasswordResetTokenDto createToken(UserDto userDto) {
		String token = generateToken();
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION_TIME_MIN);
		
		PasswordResetTokenDto resetTokenDto = new PasswordResetTokenDto();
		resetTokenDto.setUserDto(userDto);
		resetTokenDto.setExpiryDate(expiryDate);
		resetTokenDto.setToken(token);
		
		PasswordResetToken savedResetToken = this.passwordResetTokenRepo.save(this.modelMapper.map(resetTokenDto, PasswordResetToken.class));
		
		return this.modelMapper.map(savedResetToken,PasswordResetTokenDto.class);
	}

	@Override
	public boolean validateToken(String token) {
		PasswordResetToken resetToken = this.passwordResetTokenRepo.findByToken(token).orElse(null);
        return resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now());
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		PasswordResetToken resetToken = this.passwordResetTokenRepo.findByToken(token).orElse(null);
        if (resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
            User user = resetToken.getUser();
            user.setPassword(this.passwordEncoder.encode(newPassword));
            // Save the updated user password in the UserRepository
            this.userRepo.save(user);
            
            // Delete the password reset token after successful reset
            this.passwordResetTokenRepo.delete(resetToken);
        }
	}
	
	private String generateToken() {
		// Implement token generation logic here
		System.out.println();
        return UUID.randomUUID().toString();
	}

	
}
