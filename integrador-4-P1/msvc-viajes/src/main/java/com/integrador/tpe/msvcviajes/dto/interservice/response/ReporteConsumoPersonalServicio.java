package com.integrador.tpe.msvcviajes.dto.interservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteConsumoPersonalServicio {
    private Double cantidadHorasServicio;
    private Double kmRecorridos;
}
