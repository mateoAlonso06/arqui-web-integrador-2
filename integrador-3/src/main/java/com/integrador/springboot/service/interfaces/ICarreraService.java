package com.integrador.springboot.service.interfaces;

import com.integrador.springboot.dto.requestDTO.CarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.CarreraResponseDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICarreraService {
    CarreraResponseDTO addCarrera(CarreraRequestDTO carrera);

    Page<CarreraResponseDTO> getCarrerasConInscriptos(Pageable pageable);

    List<EstudianteResponseDTO> getEstudiantesByCarreraId(Integer idCarrera, String ciudad);

    void deleteCarrera(Integer idCarrera);
}