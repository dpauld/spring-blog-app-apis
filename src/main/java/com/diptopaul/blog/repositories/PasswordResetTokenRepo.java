package com.diptopaul.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diptopaul.blog.entities.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Integer> {
	Optional<PasswordResetToken> findByToken(String token);
}
