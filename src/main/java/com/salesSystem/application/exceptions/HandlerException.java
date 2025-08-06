package com.salesSystem.application.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class HandlerException extends RuntimeException {

    @ExceptionHandler(EntityNotFoundException.class)

    public ResponseEntity error404() {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity error400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(FieldsErrors::new).toList());
    }

    private record FieldsErrors(String field, String message) {
        public FieldsErrors(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(ValidationException.class)

    public ResponseEntity<String> errorValidation(ValidationException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
