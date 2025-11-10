package com.integrador.tpe.msvcviajes.dto.interservice.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ViajeReporteRequestDTO(
        @NotNull @Positive
        Integer cantidadViajes,
        @NotNull @Positive
        Integer anio
) {
}
