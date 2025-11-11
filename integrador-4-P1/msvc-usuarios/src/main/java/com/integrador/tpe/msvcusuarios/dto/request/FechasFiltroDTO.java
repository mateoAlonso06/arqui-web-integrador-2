package com.integrador.tpe.msvcusuarios.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FechasFiltroDTO(
        @NotNull
        LocalDateTime fechaInicio,
        @NotNull
        LocalDateTime fechaFin
) {
}
