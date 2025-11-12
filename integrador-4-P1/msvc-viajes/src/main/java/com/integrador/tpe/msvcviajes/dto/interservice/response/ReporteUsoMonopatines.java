package com.integrador.tpe.msvcviajes.dto.interservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReporteUsoMonopatines {
    private String idMonopatin;
    private Long cantidadViajes;
    private Double kmRecorridos;
    private Double tiempoPausa;

    public ReporteUsoMonopatines(String idMonopatin, Long cantidadViajes, Double kmRecorridos) {
        this.idMonopatin = idMonopatin;
        this.cantidadViajes = cantidadViajes;
        this.kmRecorridos = kmRecorridos;
    }
}
