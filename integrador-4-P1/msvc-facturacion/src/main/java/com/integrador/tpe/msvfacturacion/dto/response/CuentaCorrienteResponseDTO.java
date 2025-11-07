package com.integrador.tpe.msvfacturacion.dto.response;

import java.math.BigDecimal;

public record CuentaCorrienteResponseDTO(
        Long id,
        BigDecimal saldoActual
) {
}
