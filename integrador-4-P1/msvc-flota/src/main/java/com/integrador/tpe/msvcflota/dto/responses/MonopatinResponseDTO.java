package com.integrador.tpe.msvcflota.dto.responses;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;

public record MonopatinResponseDTO(
        String id,
        UbicacionGPS ubicacionGps,
        Double kmRecorridos,
        EstadoMonopatin estado
) {
}
