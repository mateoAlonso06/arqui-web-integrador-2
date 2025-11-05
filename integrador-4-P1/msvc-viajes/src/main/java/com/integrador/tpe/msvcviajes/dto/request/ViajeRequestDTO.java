package com.integrador.tpe.msvcviajes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ViajeRequestDTO(
        @NotNull @Positive
        Long idMonopatin,
        @NotNull @Positive
        Long idCuenta,
        @NotNull @Positive
        Long idUsuario
) {
}
