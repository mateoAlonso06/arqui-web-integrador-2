package com.integrador.tpe.msvcgroq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaConsultaDTO {
    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("contenido")
    private String contenido;

    @JsonProperty("datosOriginales")
    private Map<String, Object> datosOriginales;

    @JsonProperty("tiempoGeneracionMs")
    private Long tiempoGeneracionMs;
}