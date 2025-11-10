package com.integrador.tpe.msvcflota.dto.request;

import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParadaRequestDTO(
        @NotNull(message = "La ubicación GPS no puede ser nula")
        UbicacionGPS ubicacionGps,
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre
) {
}
