package com.integrador.tpe.msvcusuarios.dto.response;

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
    private Long idUsuario;
    private Long cantidadHorasServicio;
    private Double kmRecorridos;
    private LocalDateTime periodoInicio;
    private LocalDateTime periodoFin;
    private InfoCuentasReporte cuentasAsociadas;
}
