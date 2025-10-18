package org.integrador.repository;

import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Estudiante;

import java.util.List;

public interface IEstudianteRepository {
    EstudianteDTO addEstudiante(Estudiante estudiante);

    List<EstudianteDTO> getAllEstudiantes(int page, int size);

    EstudianteDTO getEstudianteByLibretaUniversitaria(String libretaUniversitaria);

    List<EstudianteDTO> getEstudiantesByGenero(String genero);
}
