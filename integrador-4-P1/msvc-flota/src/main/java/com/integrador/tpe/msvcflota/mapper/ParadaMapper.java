package com.integrador.tpe.msvcflota.mapper;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.entity.Parada;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParadaMapper {
    Parada toEntity(ParadaRequestDTO paradaRequestDTO);

    @Mapping(target = "id", expression = "java(mapId(parada.getId()))")
    ParadaResponseDTO toResponseDTO(Parada parada);

    default String mapId(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }
}
