package com.diptopaul.blog.payloads;

import java.time.LocalDateTime;

import com.diptopaul.blog.entities.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PasswordResetTokenDto {
	private long id;
	private String token;
	private UserDto userDto;
	private LocalDateTime expiryDate;
}