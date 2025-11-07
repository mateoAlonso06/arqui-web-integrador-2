package com.integrador.tpe.msvcviajes.dto.interservice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class MonopatinResponseDTO {
    private Long id;
    private UbicacionGPS ubicacionGPS; // TODO: descomponer objeto si sobra tiempo
    private Double kmRecorridos;
    private EstadoMonopatin estado;
    private Long idParada;
    private String nombreParada;
}
