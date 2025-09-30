package org.integrador.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EstudianteDTO {
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private Integer edad;
    private String genero;
    private String ciudad;
    private String libretaUniversitaria;
}
