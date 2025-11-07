package com.integrador.tpe.msvcviajes.dto.interservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionViaje {
    private Long idViaje;
    private Long idMonopatin;
    private Long idUsuario;
    private Long idCuenta;
    private String tipoCuenta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double kmHechosPorElUsuario;
}
