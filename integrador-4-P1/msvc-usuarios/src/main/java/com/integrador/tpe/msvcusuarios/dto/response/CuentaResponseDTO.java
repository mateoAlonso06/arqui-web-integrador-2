package com.integrador.tpe.msvcusuarios.dto.response;

import com.integrador.tpe.msvcusuarios.enums.EstadoCuenta;

public record CuentaResponseDTO(
        Long id,
        Long idCuentaMercadoPago,
        Boolean estadoCuenta,
        EstadoCuenta estadoCenta
) {
}
