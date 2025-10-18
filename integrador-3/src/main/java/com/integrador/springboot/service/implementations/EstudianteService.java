package com.integrador.springboot.service.implementations;

import com.integrador.springboot.dto.requestDTO.EstudianteRequestDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import com.integrador.springboot.entity.Estudiante;
import com.integrador.springboot.exception.EstudianteNotFoundException;
import com.integrador.springboot.mapper.EstudianteMapper;
import com.integrador.springboot.repository.IEstudianteRepository;
import com.integrador.springboot.service.interfaces.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteService implements IEstudianteService {
    private final IEstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    @Transactional
    public EstudianteResponseDTO addEstudiante(EstudianteRequestDTO estudiante) {
        if (estudianteRepository.existsById(estudiante.dni()))
            throw new IllegalArgumentException("El estudiante ya esta registrado");

        Estudiante estudianteToSave = estudianteMapper.toEntity(estudiante);
        Estudiante saved = estudianteRepository.save(estudianteToSave);

        return estudianteMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public EstudianteResponseDTO getEstudianteById(String id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new EstudianteNotFoundException("El estudiante con ID: " + id + " no existe"));

        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstudianteResponseDTO> getEstudiantes(Pageable pageable) {
        Page<Estudiante> estudiantes = estudianteRepository.findAll(pageable);
        return estudiantes.map(estudianteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponseDTO getEstudianteByLibretaUniversitaria(String libretaUniversitaria) {
        Estudiante estudiante = estudianteRepository.findByLibretaUniversitaria(libretaUniversitaria)
                .orElseThrow(() -> new EstudianteNotFoundException("El estudiante con libreta universtaria: " + libretaUniversitaria + " no existe"));

        return estudianteMapper.toDTO(estudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> getEstudiantesByGenero(String genero) {
        List<EstudianteResponseDTO> estudiantesDTO = new ArrayList<>();
        List<Estudiante> estudiantes = estudianteRepository.findByGenero(genero);

        estudiantes.stream().map(estudianteMapper::toDTO).toList();

        return estudiantesDTO;
    }
}
