package org.integrador.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EstudianteCarreraDTO {
    private String idEstudiante;
    private Integer idCarrera;
    private Integer anioInscripcion;
    private Integer graduacion;
    private Integer antiguedad;
}
