package com.integrador.tpe.msvcgroq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosExtraidosDTO {
    private String idParada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String tipoConsulta;
}
