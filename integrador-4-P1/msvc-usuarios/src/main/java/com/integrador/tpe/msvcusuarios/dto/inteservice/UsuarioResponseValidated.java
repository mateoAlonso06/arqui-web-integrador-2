package com.integrador.tpe.msvcusuarios.dto.inteservice;

import com.integrador.tpe.msvcusuarios.entity.Role;
import com.integrador.tpe.msvcusuarios.entity.TipoCuenta;

public record UsuarioResponseValidated(
        Long id,
        String email,
        Role role,
        TipoCuenta tipoCuenta
) {
}
