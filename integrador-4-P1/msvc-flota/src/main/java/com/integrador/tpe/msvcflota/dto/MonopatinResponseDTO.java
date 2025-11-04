package com.integrador.tpe.msvcflota.dto;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.ParadaInfo;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import org.bson.types.ObjectId;

public record MonopatinResponseDTO(
        ObjectId id,
        UbicacionGPS ubicacionGps,
        Double kmRecorridos,
        EstadoMonopatin estado,
        ParadaInfo paradaInfo
) {
}
