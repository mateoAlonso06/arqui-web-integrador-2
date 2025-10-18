package com.integrador.springboot.mapper;

import com.integrador.springboot.dto.requestDTO.CarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.CarreraResponseDTO;
import com.integrador.springboot.entity.Carrera;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarreraMapper {
    CarreraResponseDTO toCarreraDTO(Carrera carrera);

    Carrera toEntity(CarreraRequestDTO carreraRequestDTO);
}

