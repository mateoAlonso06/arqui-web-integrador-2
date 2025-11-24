package com.integrador.tpe.msvcgateway.dto;

public record UsuarioResponseDTO(
        Long id,
        String email,
        Role role,
        TipoCuenta tipoCuenta
) {
}
