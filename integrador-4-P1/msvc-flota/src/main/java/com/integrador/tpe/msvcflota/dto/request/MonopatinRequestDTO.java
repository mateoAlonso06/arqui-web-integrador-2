package com.integrador.tpe.msvcflota.dto.request;

import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record MonopatinRequestDTO(
        @NotNull
        UbicacionGPS ubicacionGps, // campos internos pueden ser null, solucionar
        @PositiveOrZero
        Double kmRecorridos
) {
}
