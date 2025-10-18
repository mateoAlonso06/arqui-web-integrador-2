package com.integrador.springboot.exception;

public class CarreraNotFoundException extends RuntimeException {
    public CarreraNotFoundException(String message) {
        super(message);
    }
}
