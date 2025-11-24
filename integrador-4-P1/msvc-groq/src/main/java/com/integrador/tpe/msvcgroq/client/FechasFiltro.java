package com.integrador.tpe.msvcgroq.client;

import java.time.LocalDateTime;

public record FechasFiltro(
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
