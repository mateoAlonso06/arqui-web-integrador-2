package com.integrador.springboot.service.interfaces;

import com.integrador.springboot.dto.requestDTO.EstudianteCarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.Reporte;

import java.util.List;

public interface IEstudianteCarreraService {
    void matricularEstudiante(Integer idCarrera, String idEstudiante, EstudianteCarreraRequestDTO  estudianteCarreraRequestDTO);

    void darDeBajaEstudiante(Integer idCarrera, String idEstudiante);

    List<Reporte> getReportes();
}
