package com.integrador.tpe.msvcusuarios.dto.response;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String celular,
        String email
) {
}
