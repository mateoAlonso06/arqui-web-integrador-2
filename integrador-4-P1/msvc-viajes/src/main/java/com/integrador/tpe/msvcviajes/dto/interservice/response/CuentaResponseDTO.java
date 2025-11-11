package com.integrador.tpe.msvcviajes.dto.interservice.response;

import com.integrador.tpe.msvcviajes.dto.interservice.request.TipoCuenta;

import java.io.Serializable;

public record CuentaResponseDTO(
        Long id,
        Long idCuentaMercadoPago,
        Boolean estadoCuenta,
        TipoCuenta tipoCuenta
) implements Serializable {
}
