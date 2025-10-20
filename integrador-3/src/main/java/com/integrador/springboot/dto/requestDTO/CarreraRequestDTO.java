package com.integrador.springboot.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CarreraRequestDTO(
        @NotBlank
        String nombre,
        @NotBlank @Positive
        Integer duracion
) {
}
