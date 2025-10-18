package com.integrador.springboot.exception;

public class EstudianteNotFoundException extends RuntimeException {
    public EstudianteNotFoundException(String message) {
        super(message);
    }
}
