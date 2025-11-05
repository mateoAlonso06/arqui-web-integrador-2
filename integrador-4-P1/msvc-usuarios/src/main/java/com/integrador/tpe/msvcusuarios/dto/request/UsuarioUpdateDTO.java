package com.integrador.tpe.msvcusuarios.dto.request;

public record UsuarioUpdateDTO(
        String nombre,
        String apellido,
        String celular,
        String email
) {
}
