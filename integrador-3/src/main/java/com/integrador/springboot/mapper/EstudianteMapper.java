package com.integrador.springboot.mapper;

import com.integrador.springboot.dto.requestDTO.EstudianteRequestDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import com.integrador.springboot.entity.Estudiante;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {
    EstudianteResponseDTO toDTO(Estudiante estudiante);

    Estudiante toEntity(EstudianteRequestDTO estudiante);
}
