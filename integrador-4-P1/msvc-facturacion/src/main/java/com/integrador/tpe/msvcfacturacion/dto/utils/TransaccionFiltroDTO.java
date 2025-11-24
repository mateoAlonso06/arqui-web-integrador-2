package com.integrador.tpe.msvcfacturacion.dto.utils;

import java.time.LocalDateTime;

public record TransaccionFiltroDTO(
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin) {
}
