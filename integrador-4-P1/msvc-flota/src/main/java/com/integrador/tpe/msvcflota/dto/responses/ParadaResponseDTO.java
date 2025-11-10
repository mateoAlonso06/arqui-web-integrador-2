package com.integrador.tpe.msvcflota.dto.responses;

import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import org.bson.types.ObjectId;

public record ParadaResponseDTO(
        String id,
        UbicacionGPS ubicacionGps,
        String nombre
) {
}
