package com.integrador.tpe.msvcgroq.dto.interservice;

import com.integrador.tpe.msvcgroq.model.utils.EstadoMonopatin;
import com.integrador.tpe.msvcgroq.model.utils.UbicacionGPS;

public record MonopatinResponseDTO(
        String id,
        UbicacionGPS ubicacionGps,
        Double kmRecorridos,
        EstadoMonopatin estado
) {
}
