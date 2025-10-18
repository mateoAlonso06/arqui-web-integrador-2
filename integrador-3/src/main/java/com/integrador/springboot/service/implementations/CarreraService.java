package com.integrador.springboot.service.implementations;

import com.integrador.springboot.dto.requestDTO.CarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.CarreraResponseDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import com.integrador.springboot.entity.Carrera;
import com.integrador.springboot.entity.Estudiante;
import com.integrador.springboot.exception.CarreraNotFoundException;
import com.integrador.springboot.mapper.CarreraMapper;
import com.integrador.springboot.mapper.EstudianteMapper;
import com.integrador.springboot.repository.ICarreraRepository;
import com.integrador.springboot.service.interfaces.ICarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarreraService implements ICarreraService {
    private final ICarreraRepository carreraRepository;
    private final CarreraMapper carreraMapper;
    private final EstudianteMapper estudianteMapper;

    @Override
    @Transactional
    public CarreraResponseDTO addCarrera(CarreraRequestDTO carrera) {
        Carrera toSave = carreraMapper.toEntity(carrera);
        Carrera carreraSaved = carreraRepository.save(toSave);
        return carreraMapper.toCarreraDTO(carreraSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarreraResponseDTO> getCarrerasConInscriptos(Pageable pageable) {
        return carreraRepository.getCarrerasConInscriptos(pageable).map(carreraMapper::toCarreraDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> getEstudiantesByCarreraId(Integer idCarrera, String ciudad) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new CarreraNotFoundException("No existe carrera con ID: " + idCarrera));

        List<Estudiante> estudiantes = carreraRepository.getEstudiantesByCarrera(carrera, ciudad);
        return estudiantes.stream().map(estudianteMapper::toDTO).toList();
    }

    @Override
    @Transactional
    public void deleteCarrera(Integer idCarrera) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new CarreraNotFoundException("No existe carrera con ID: " + idCarrera));

        carreraRepository.delete(carrera);
    }
}
