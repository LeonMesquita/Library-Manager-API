package com.library_manager.api.exceptions;

public class GenericConflictException extends RuntimeException{
    public GenericConflictException(String message) {
        super(message);
    }
}
