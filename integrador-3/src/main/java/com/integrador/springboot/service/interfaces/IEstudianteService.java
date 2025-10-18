package com.integrador.springboot.service.interfaces;

import com.integrador.springboot.dto.requestDTO.EstudianteRequestDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEstudianteService {
    EstudianteResponseDTO addEstudiante(EstudianteRequestDTO estudiante);

    Page<EstudianteResponseDTO> getEstudiantes(Pageable pageable);

    EstudianteResponseDTO getEstudianteByLibretaUniversitaria(String libretaUniversitaria);

    List<EstudianteResponseDTO> getEstudiantesByGenero(String genero);
}
