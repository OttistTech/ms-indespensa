package com.ottistech.indespensa.api.ms_indespensa.exception;

public class UserAlreadyDeactivatedException extends RuntimeException {

    public UserAlreadyDeactivatedException(String message) {
        super(message);
    }
}
