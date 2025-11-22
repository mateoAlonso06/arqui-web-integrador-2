package com.integrador.tpe.msvcgateway.dto;

public record LoginResponse(
        String token,
        String username,
        String role,
        boolean success,
        String error
) {
}
