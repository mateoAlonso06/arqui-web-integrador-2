package com.integrador.tpe.msvfacturacion.dto.response;

import java.math.BigDecimal;

public record TransaccionResponseDTO(
        Long id,
        Long idCuenta,
        BigDecimal monto,
        Long idViaje
) {
}
