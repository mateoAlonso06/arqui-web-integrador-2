package com.integrador.tpe.msvcgateway.exception;

import com.integrador.tpe.msvcgateway.dto.response.LoginResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignException.Unauthorized.class)
    public ResponseEntity<LoginResponse> handleUnauthorized(FeignException.Unauthorized ignored) {
        LoginResponse response = new LoginResponse(
                null,
                null,
                null,
                false,
                "Usuario o contraseña incorrectos"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<LoginResponse> handleFeignErrors(FeignException ex) {
        LoginResponse response = new LoginResponse(
                null,
                null,
                null,
                false,
                "Error al comunicarse con el servicio de usuarios"
        );
        return ResponseEntity.status(ex.status()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("error", "Error inesperado en el gateway");
        body.put("detail", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
