package com.integrador.tpe.msvcusuarios.dto.request;

import com.integrador.tpe.msvcusuarios.entity.TipoCuenta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CuentaRequestDTO(
        @Positive(message = "El id debe ser mayor que 0")
        @NotNull
        Long idCuentaMercadoPago,
        TipoCuenta tipoCuenta
) {
    public CuentaRequestDTO {
        if (tipoCuenta == null) {
            tipoCuenta = TipoCuenta.BASICA;
        }
    }
}
