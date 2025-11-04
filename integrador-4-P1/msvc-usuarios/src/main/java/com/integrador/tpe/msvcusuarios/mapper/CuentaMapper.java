package com.integrador.tpe.msvcusuarios.mapper;

import com.integrador.tpe.msvcusuarios.dto.CuentaRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.CuentaResponseDTO;
import com.integrador.tpe.msvcusuarios.entity.Cuenta;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaResponseDTO toResponse(Cuenta cuenta);

    Cuenta toEntity(CuentaRequestDTO cuentaRequestDTO);
}
