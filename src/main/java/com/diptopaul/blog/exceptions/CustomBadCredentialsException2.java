package com.diptopaul.blog.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class CustomBadCredentialsException2 extends BadCredentialsException {

    public CustomBadCredentialsException2(String message) {
        super(message);
    }

    public CustomBadCredentialsException2(String message, Throwable cause) {
        super(message, cause);
    }
}