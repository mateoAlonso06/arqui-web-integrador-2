package com.integrador.tpe.msvcviajes.dto.interservice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class ParadaResponseDTO {
    private Long id;
    private UbicacionGPS ubicacionGPS;
    private String nombre;
}
