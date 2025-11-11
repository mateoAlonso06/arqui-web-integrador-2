package com.integrador.tpe.msvcreportes.dto;

import java.math.BigDecimal;

public record TransaccionResponseDTO(
        Long id,
        Long idCuenta,
        BigDecimal monto,
        Long idViaje
) {
}
