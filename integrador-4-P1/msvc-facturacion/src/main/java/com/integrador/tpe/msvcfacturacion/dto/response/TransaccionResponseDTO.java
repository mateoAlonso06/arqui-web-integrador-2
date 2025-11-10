package com.integrador.tpe.msvcfacturacion.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record TransaccionResponseDTO(
        Long id,
        Long idCuenta,
        BigDecimal monto,
        Long idViaje
) implements Serializable {
}
