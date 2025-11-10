package com.integrador.tpe.msvcreportes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ViajeReporteRequestDTO(
        @NotNull @Positive
        Integer cantidadViajes,
        @NotNull @Positive
        Integer anio
) {
}
