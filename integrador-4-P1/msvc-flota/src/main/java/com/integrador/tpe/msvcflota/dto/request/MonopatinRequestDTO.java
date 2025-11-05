package com.integrador.tpe.msvcflota.dto.request;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.ParadaInfo;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MonopatinRequestDTO(
        @NotNull
        UbicacionGPS ubicacionGPS,
        @NotNull @Positive
        Double kmRecorridos,
        @NotNull
        EstadoMonopatin estadoMonopatin,
        ParadaInfo paradaInfo // puede ser null
) {
}
