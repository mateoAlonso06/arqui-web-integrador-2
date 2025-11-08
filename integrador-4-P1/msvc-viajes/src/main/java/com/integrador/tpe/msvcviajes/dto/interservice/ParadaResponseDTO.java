package com.integrador.tpe.msvcviajes.dto.interservice;

import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadaResponseDTO {
    private Long id;
    private UbicacionGPS ubicacionGPS;
}
