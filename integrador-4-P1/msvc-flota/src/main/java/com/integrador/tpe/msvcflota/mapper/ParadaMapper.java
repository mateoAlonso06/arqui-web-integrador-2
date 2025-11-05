package com.integrador.tpe.msvcflota.mapper;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.entity.Parada;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParadaMapper {
    Parada toEntity(ParadaRequestDTO paradaRequestDTO);

    ParadaResponseDTO toResponseDTO(Parada parada);
}
