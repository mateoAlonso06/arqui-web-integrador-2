package com.integrador.tpe.msvcviajes.dto.interservice.response;

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
    private Integer cantidadViajes;
    private Double kmRecorridos;
}
