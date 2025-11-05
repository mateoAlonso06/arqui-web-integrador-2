package com.integrador.tpe.msvcviajes.dto.interservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long id;
//    private UbicacionGPS ubicacionGps;
    private Double kmRecorridos;
    private EstadoMonopatin estado;
    private ParadaInfo paradaInfo;
}
