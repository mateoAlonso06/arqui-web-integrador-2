package com.integrador.springboot.dto.responseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CarreraResponseDTO(
        @NotBlank
        Integer id,
        @NotBlank
        String nombre,
        @NotBlank @Positive
        Integer duracion
) {
}
