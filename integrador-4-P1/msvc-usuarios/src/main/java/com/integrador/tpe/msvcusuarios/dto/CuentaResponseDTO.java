package com.integrador.tpe.msvcusuarios.dto;

import com.integrador.tpe.msvcusuarios.enums.EstadoCuenta;

public record CuentaResponseDTO(
        Long id,
        Long idCuentaMercadoPago,
        Boolean estadoCuenta
) {
}
