package com.diptopaul.blog.entities;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
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
@Entity
public class PasswordResetToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String token;
	
	@ManyToOne(targetEntity = User.class,fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	private LocalDateTime expiryDate;//LocalDateTime (Java 8+) instead of old Date api
}
