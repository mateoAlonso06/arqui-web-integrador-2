package com.integrador.tpe.msvcusuarios.dto.request;

import com.integrador.tpe.msvcusuarios.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotBlank
        String celular,
        @NotBlank @Email
        String email,
        Role role
) {
}
