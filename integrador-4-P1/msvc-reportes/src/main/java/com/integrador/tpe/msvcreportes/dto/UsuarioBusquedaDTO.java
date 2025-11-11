package com.integrador.tpe.msvcreportes.dto;

import com.integrador.tpe.msvcreportes.model.TipoCuenta;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UsuarioBusquedaDTO(
        @NotNull
        LocalDateTime fechaInicio,
        @NotNull
        LocalDateTime fechaFin,
        @NotNull
        TipoCuenta tipoCuenta
) implements Serializable {
}
