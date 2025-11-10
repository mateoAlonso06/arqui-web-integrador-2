package com.integrador.tpe.msvctarifas.mapper;

import com.integrador.tpe.msvctarifas.dto.request.TarifaRequestDTO;
import com.integrador.tpe.msvctarifas.dto.response.TarifaResponseDTO;
import com.integrador.tpe.msvctarifas.entity.Tarifa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarifaMapper {
    Tarifa toEntity(TarifaRequestDTO tarifa);

    TarifaResponseDTO toResponseDTO(Tarifa tarifa);
}
