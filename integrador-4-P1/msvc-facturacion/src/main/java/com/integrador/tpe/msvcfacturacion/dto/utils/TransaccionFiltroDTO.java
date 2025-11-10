package com.integrador.tpe.msvcfacturacion.dto.utils;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransaccionFiltroDTO {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
