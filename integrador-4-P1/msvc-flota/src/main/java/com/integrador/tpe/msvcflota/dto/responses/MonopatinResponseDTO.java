package com.integrador.tpe.msvcflota.dto.responses;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import org.bson.types.ObjectId;

public record MonopatinResponseDTO(
        String id,
        UbicacionGPS ubicacionGps,
        Double kmRecorridos,
        EstadoMonopatin estado
) {
}
