package com.integrador.tpe.msvcfacturacion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InformacionViaje implements Serializable {
    private Long idViaje;
    private String idMonopatin;
    private Long idUsuario;
    private Long idCuenta;
    private String tipoCuenta;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double tiempoDePausa;
    private Double kmHechosPorElUsuario;
    private Long duracionViaje;
}