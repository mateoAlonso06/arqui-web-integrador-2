package org.integrador.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reporte {
    private String nombreCarrera;
    private Long cantidadInscriptos;
    private Long cantidadGraduados;
    private Integer anio;
}
