package com.integrador.tpe.msvcusuarios.dto.response;

import com.integrador.tpe.msvcusuarios.entity.Role;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String apellido,
        String celular,
        String email,
        Role role
) {
}
