package com.integrador.springboot.dto.responseDTO;

public record EstudianteCarreraResponseDTO(
        String idEstudiante,
        Integer idCarrera,
        Integer antiguedad,
        Integer anioInscripcion,
        Integer graduacion
) {
}
