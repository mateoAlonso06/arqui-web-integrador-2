package com.integrador.springboot.mapper;

import com.integrador.springboot.dto.requestDTO.EstudianteCarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteCarreraResponseDTO;
import com.integrador.springboot.entity.EstudianteCarrera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EstudianteCarreraMapper {
    EstudianteCarrera toEntity(EstudianteCarreraRequestDTO  estudianteCarreraRequestDTO);

    @Mapping(target = "idEstudiante", source = "estudiante.dni")
    @Mapping(target = "idCarrera", source = "carrera.id")
    EstudianteCarreraResponseDTO toDTO(EstudianteCarrera estudianteCarrera);
}
