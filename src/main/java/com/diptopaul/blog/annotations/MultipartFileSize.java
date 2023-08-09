package com.diptopaul.blog.annotations;

import java.lang.annotation.*;

import com.diptopaul.blog.annotations.constraints.MultipartFileSizeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = MultipartFileSizeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipartFileSize {
	
    String message() default "Exceeds the maximum allowed size.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    long max() default Long.MAX_VALUE;
}

