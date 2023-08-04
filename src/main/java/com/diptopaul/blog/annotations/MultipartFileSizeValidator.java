package com.diptopaul.blog.annotations;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSize, MultipartFile> {

    private long maxFileSize;

    @Override
    public void initialize(MultipartFileSize constraintAnnotation) {
        this.maxFileSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || file.getSize() <= maxFileSize;
    }
}

