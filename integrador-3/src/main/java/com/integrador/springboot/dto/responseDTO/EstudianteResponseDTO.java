package com.integrador.springboot.dto.responseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EstudianteResponseDTO(
        @NotBlank
        String dni,
        @NotBlank
        String nombre,
        @NotBlank
        String apellido,
        @NotNull @Positive
        Integer edad,
        @NotBlank
        String genero,
        @NotBlank
        String ciudad,
        @NotBlank
        String libretaUniversitaria
) {
}
