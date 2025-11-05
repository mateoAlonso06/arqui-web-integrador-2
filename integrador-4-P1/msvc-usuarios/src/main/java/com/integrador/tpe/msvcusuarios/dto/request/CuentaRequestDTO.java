package com.integrador.tpe.msvcusuarios.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CuentaRequestDTO(
        @Positive(message = "El id debe ser mayor que 0") @NotNull
        Long idCuentaMercadoPago
) {
}
