package com.library_manager.api.exceptions;

public class GenericBadRequestException extends RuntimeException {
    public GenericBadRequestException(String message) {
        super(message);
    }
}
