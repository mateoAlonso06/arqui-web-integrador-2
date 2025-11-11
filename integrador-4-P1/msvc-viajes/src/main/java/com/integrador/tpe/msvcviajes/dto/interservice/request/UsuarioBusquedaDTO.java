package com.integrador.tpe.msvcviajes.dto.interservice.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UsuarioBusquedaDTO(
        @NotNull
        LocalDateTime fechaInicio,
        @NotNull
        LocalDateTime fechaFin,
        @NotNull
        TipoCuenta tipoCuenta
) {
}
