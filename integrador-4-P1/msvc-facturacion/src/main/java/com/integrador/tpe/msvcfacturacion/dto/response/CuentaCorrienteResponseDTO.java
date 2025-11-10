package com.integrador.tpe.msvcfacturacion.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record CuentaCorrienteResponseDTO(
        Long idCuenta,
        BigDecimal saldoActual
) implements Serializable {
}
