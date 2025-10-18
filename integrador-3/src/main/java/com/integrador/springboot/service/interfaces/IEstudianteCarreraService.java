package com.integrador.springboot.service.interfaces;

import com.integrador.springboot.dto.requestDTO.EstudianteCarreraRequestDTO;

public interface IEstudianteCarreraService {
    void matricularEstudiante(Integer idCarrera, String idEstudiante, EstudianteCarreraRequestDTO  estudianteCarreraRequestDTO);

    void darDeBajaEstudiante(Integer idCarrera, String idEstudiante);
}
