package com.integrador.tpe.msvcviajes.dto.interservice.response;

public record ReporteUsoMonopatines(
        String idMonopatin,
        Long cantidadViajes,
        Double kmRecorridos,
        Double tiempoPausa
) {
}
