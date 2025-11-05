package com.integrador.tpe.msvcflota.mapper;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonopatinMapper {
    Monopatin toEntity(MonopatinRequestDTO monopatinRequestDTO);

    MonopatinResponseDTO toResponseDTO(Monopatin monopatin);
}
