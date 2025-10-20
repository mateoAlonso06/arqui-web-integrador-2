package com.integrador.springboot.dto.responseDTO;

public record Reporte(
        String nombreCarrera,
        Long cantidadInscriptos,
        Long cantidadGraduados,
        Integer anio
) {
}