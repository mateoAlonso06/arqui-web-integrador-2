package com.integrador.tpe.msvcusuarios.dto.inteservice;

import com.integrador.tpe.msvcusuarios.entity.Role;

public record UsuarioResponseValidated(
        Long id,
        String email,
        Role role
) {
}
