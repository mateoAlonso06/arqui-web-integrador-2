package com.integrador.springboot.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CarreraRequestDTO(
        @NotBlank
        Integer id,
        @NotBlank
        String nombre,
        @NotBlank @Positive
        Integer duracion,
        @NotBlank
        Integer cantidadInscriptos
) {
}
