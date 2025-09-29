package org.integrador.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EstudianteDTO {
    private final Long id;
    private final String nombre;
    private final String apellido;
    private final String email;
    private final Integer edad;
    private final String genero;
    private final String ciudad;
    private final String numeroLegajo;
}
