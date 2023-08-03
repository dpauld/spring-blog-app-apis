package com.diptopaul.blog.annotations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.UserRepo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;


@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired UserRepo userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
    	if (email == null) {
            return true; // Assume null values are valid (can add additional checks if needed)
        }
    	System.out.println(email);
        String lowerCaseEmail = email.toLowerCase(); // Normalize email to lowercase for comparison
     
        User user = this.userRepository.findByEmail(lowerCaseEmail).orElse(null);
        
        return user==null;
    }
}