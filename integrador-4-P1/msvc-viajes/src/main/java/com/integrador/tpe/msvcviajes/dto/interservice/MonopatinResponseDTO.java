package com.integrador.tpe.msvcviajes.dto.interservice;

import com.integrador.tpe.msvcviajes.dto.interservice.utils.EstadoMonopatin;
import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonopatinResponseDTO {
    private Long id;
    private UbicacionGPS ubicacionGPS; // TODO: descomponer objeto si sobra tiempo
    private Double kmRecorridos;
    private EstadoMonopatin estado;
    private Long idParada;
    private String nombreParada;
}
