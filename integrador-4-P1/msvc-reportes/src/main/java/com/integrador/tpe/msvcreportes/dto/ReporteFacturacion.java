package com.integrador.tpe.msvcreportes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReporteFacturacion(
        BigDecimal totalFacturado,
        Integer totalTransacciones,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
