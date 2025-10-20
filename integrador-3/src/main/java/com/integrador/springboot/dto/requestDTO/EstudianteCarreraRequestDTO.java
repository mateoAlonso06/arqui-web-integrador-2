package com.integrador.springboot.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EstudianteCarreraRequestDTO(
        @NotNull @Positive
        Integer anioInscripcion,
        @NotNull @Positive
        Integer graduacion,
        @NotNull @Positive
        Integer antiguedad
) {
}
