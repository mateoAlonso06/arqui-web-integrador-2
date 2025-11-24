package com.integrador.tpe.msvcgroq.dto.interservice;

import java.time.LocalDateTime;

public record ReporteConsumoPersonal(
        Long idUsuario,
        Long cantidadHorasServicio,
        Double kmRecorridos,
        LocalDateTime periodoInicio,
        LocalDateTime periodoFin
) {

}
