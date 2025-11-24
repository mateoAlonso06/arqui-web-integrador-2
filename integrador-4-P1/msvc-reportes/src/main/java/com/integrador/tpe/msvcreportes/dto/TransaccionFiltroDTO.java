package com.integrador.tpe.msvcreportes.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransaccionFiltroDTO(
        @NotNull
        LocalDateTime fechaInicio,
        @NotNull
        LocalDateTime fechaFin
) {
}
