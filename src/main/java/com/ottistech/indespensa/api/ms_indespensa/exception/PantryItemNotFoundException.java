package com.ottistech.indespensa.api.ms_indespensa.exception;

public class PantryItemNotFoundException extends RuntimeException {
    public PantryItemNotFoundException(String message) {
        super(message);
    }
}
