package com.ottistech.indespensa.api.ms_indespensa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());

        problemDetail.setTitle("Email already in use");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setStatus(HttpStatus.CONFLICT);

        return problemDetail;
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail handleIncorrectPasswordException(IncorrectPasswordException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setTitle("Incorrect password");

        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyDeactivatedException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ProblemDetail handleUserAlreadyDeactivatedException(UserAlreadyDeactivatedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.GONE, ex.getMessage());
        problemDetail.setTitle("User already deactivated");

        return problemDetail;
    }

    @ExceptionHandler(JsonParcealizationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ProblemDetail handleJsonParcealizationException(JsonParcealizationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
        problemDetail.setTitle("Json Parcealization Exception");

        return problemDetail;
    }
      
    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(ResponseStatusException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatusCode(), ex.getReason());

        problemDetail.setTitle("Error occurred");
        problemDetail.setDetail(ex.getReason());

        return problemDetail;
    }

    @ExceptionHandler(UserAlreadyIsPremiumException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ProblemDetail handleUserIsAlreadyPremiumException(UserAlreadyIsPremiumException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.GONE, ex.getMessage());

        problemDetail.setTitle("User already is premium");
        problemDetail.setDetail(ex.getMessage());

        return problemDetail;
    }
}
