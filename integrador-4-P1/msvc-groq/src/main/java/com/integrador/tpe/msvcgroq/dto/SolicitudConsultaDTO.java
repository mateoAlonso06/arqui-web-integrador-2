package com.integrador.tpe.msvcgroq.dto;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SolicitudConsultaDTO(
        @NotBlank(message = "La pregunta es requerida")
        @JsonProperty("pregunta")
        String pregunta
) {
}