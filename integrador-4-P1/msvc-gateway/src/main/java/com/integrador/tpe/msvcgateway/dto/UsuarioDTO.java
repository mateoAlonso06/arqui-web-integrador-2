package com.integrador.tpe.msvcgateway.dto;

public record UsuarioDTO(
        Long id,
        String username,
        String email,
        String role
) {
}
