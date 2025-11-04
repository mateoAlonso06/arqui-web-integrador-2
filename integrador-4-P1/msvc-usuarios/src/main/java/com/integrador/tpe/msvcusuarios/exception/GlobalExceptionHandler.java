package com.integrador.tpe.msvcusuarios.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = {
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        // Binding result me permita almacenar los resultados de la validacion y el binding de datos en una peticion HTTP a un objeto Java
        if (ex instanceof MethodArgumentNotValidException manve) {
            manve.getBindingResult().getFieldErrors().forEach(error -> {
                String fieldName = error.getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof ConstraintViolationException cve) {
            cve.getConstraintViolations().forEach(violation -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
