package com.diptopaul.blog.exceptions;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException(String message) {
        super(message);
    }
}
