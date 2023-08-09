package com.diptopaul.blog.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.diptopaul.blog.config.AppConstants;
import com.diptopaul.blog.repositories.PasswordResetTokenRepo;

@Component
public class PasswordResetTokenCleanup {

	private static final int SCHEDULED_DELETION_TIME_MS =  24 * 60 * 60 * 1000; // Runs every 24 hours //300000 = 5 mins;
    private final PasswordResetTokenRepo tokenRepository;

    public PasswordResetTokenCleanup(PasswordResetTokenRepo tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    //@Scheduled(cron = "0 0 * * *") //cron will run the task every hour at the start of the hour (midnight).
    @Scheduled(fixedRate = SCHEDULED_DELETION_TIME_MS)
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredAndUnusedTokens();
    }
}
