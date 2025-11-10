package com.integrador.tpe.msvcfacturacion.mapper;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransaccionMapper {
    TransaccionResponseDTO toResponseDTO(Transaccion transaccion);
}
