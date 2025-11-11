package com.integrador.tpe.msvcviajes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViajeReporteResponseDTO implements Serializable {
    private String idMonopatin;
    private Long cantidadViajes;
    private Double kmRecorridos;
}
