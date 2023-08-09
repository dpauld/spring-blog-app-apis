package com.diptopaul.blog.services.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.config.AppConstants;
import com.diptopaul.blog.entities.PasswordResetToken;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.exceptions.ResourceNotFoundException;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.PasswordResetTokenRepo;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.PasswordResetTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService{
	
	private final int EXPIRATION_TIME_MIN = AppConstants.PASSWORD_RESET_EXPIRATION_TIME_MIN;//defaut 5 min
	
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
	public void resetPassword(String token, UserDto userDto) {
		//read the corresponding entry from db
		PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token).orElseThrow(()->new ResourceNotFoundException("Token", "value", token));
		
		//this if check is not necessary, cause controller level validateToken method validated this condition too check the implementation of it avobe
		if (resetToken != null && resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
			User existingUser =  resetToken.getUser();
//			System.out.println(existingUser.toString());

			//update existingUser by the userDto that contains the changed password field
			this.modelMapper.map(userDto, existingUser);

			//encode the new password
			existingUser.setPassword(this.passwordEncoder.encode(existingUser.getPassword()));
            
			// Save the updated user password in the UserRepository
            this.userRepo.save(existingUser);
            
            // Delete the password reset token after successful reset
            this.passwordResetTokenRepo.delete(resetToken);
        }
        
	}
	
	private String generateToken() {
		// Implement token generation logic here
		System.out.println();
        return UUID.randomUUID().toString();
	}

	@Override
	public PasswordResetTokenDto getPasswordResetTokenByToken(String token) {
		PasswordResetToken resetToken = passwordResetTokenRepo.findByToken(token).orElseThrow(()->new ResourceNotFoundException("Token", "value", token));
		return this.modelMapper.map(resetToken, PasswordResetTokenDto.class);
	}

	
}
