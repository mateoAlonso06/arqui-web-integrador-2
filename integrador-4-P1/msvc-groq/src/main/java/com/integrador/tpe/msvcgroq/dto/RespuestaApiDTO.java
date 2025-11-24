package com.integrador.tpe.msvcgroq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaApiDTO<T> {
    @JsonProperty("exito")
    private boolean exito;

    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("datos")
    private T datos;

    @JsonProperty("timestamp")
    private Long timestamp;
}