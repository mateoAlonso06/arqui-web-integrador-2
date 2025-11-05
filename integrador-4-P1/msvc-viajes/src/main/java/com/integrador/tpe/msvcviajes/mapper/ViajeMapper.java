package com.integrador.tpe.msvcviajes.mapper;

import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.entity.Viaje;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ViajeMapper {
    Viaje toEntity(ViajeRequestDTO viajeRequestDTO);

    ViajeResponseDTO toResponseDTO(Viaje viaje);
}
