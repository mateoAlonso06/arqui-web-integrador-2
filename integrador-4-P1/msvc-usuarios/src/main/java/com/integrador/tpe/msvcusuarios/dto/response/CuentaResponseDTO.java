package com.integrador.tpe.msvcusuarios.dto.response;

import com.integrador.tpe.msvcusuarios.entity.TipoCuenta;

public record CuentaResponseDTO(
        Long id,
        Long idCuentaMercadoPago,
        Boolean estadoCuenta,
        TipoCuenta tipoCuenta
) {
}
