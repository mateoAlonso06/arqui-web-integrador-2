package com.integrador.tpe.msvcviajes.dto.interservice.response;

import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParadaResponseDTO {
    private String id;
    private UbicacionGPS ubicacionGps;
}
