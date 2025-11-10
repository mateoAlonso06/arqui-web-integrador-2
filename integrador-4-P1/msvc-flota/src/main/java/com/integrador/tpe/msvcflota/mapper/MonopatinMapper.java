package com.integrador.tpe.msvcflota.mapper;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MonopatinMapper {
    Monopatin toEntity(MonopatinRequestDTO monopatinRequestDTO);

    @Mapping(target = "id", expression = "java(mapId(monopatin.getId()))")
    MonopatinResponseDTO toResponseDTO(Monopatin monopatin);

    default String mapId(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }
}
