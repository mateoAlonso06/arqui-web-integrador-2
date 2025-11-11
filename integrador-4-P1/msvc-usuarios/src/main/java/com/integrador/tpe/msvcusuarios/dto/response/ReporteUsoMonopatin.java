package com.integrador.tpe.msvcusuarios.dto.response;

public record ReporteUsoMonopatin(
        String idMonopatin,
        Long cantidadViajes,
        Double kmRecorridos,
        Double tiempoPausa
) {
}
