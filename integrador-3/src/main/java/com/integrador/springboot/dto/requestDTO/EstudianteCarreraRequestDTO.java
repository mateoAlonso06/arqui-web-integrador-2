package com.integrador.springboot.dto.requestDTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EstudianteCarreraRequestDTO(
        @Positive @Size(min = 4, max = 4)
        Integer anioInsripcion,
        @Positive @Size(min = 4, max = 4)
        Integer graduacion,
        @Positive @Size(min = 4, max = 4)
        Integer antiguedad
) {
}
