package org.integrador.repository;

import org.integrador.dto.CarreraDTO;
import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Carrera;

import java.util.List;

public interface ICarreraRepository {
    CarreraDTO addCarrera(Carrera carreraDTO);

    List<CarreraDTO> getAllCarrerasConInscriptos();

    List<EstudianteDTO> getEstudiantesByCarreraId(Integer carreraId, String ciudad);
}
