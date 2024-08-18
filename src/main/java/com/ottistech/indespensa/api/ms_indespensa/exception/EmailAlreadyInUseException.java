package com.ottistech.indespensa.api.ms_indespensa.exception;

public class EmailAlreadyInUseException extends RuntimeException {

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

}
