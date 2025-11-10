package com.integrador.tpe.msvcviajes.dto.interservice.response;

import com.integrador.tpe.msvcviajes.dto.interservice.utils.EstadoMonopatin;
import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonopatinResponseDTO {
    private String id;
    private UbicacionGPS ubicacionGps; // TODO: descomponer objeto si sobra tiempo
    private Double kmRecorridos;
    private EstadoMonopatin estado;
    private String idParada;
    private String nombreParada;
}
