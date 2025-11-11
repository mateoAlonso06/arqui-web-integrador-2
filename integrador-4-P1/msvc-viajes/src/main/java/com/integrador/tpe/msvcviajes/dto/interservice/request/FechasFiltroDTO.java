package com.integrador.tpe.msvcviajes.dto.interservice.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FechasFiltroDTO(
        @NotNull
        LocalDateTime fechaInicio,
        @NotNull
        LocalDateTime fechaFin
) {
}
