package com.diptopaul.blog.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

@Deprecated
public class CustomBadCredentialsException extends BadCredentialsException {

    public CustomBadCredentialsException(String message) {
        super(message);
    }

    public CustomBadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}