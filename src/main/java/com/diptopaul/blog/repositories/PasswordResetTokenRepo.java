package com.diptopaul.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.diptopaul.blog.entities.PasswordResetToken;

public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Integer> {
	Optional<PasswordResetToken> findByToken(String token);
	
	@Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.expiryDate <= CURRENT_TIMESTAMP")
    void deleteExpiredAndUnusedTokens();
}
