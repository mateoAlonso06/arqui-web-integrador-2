package com.integrador.tpe.msvcfacturacion.mapper;

import com.integrador.tpe.msvcfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvcfacturacion.entity.CuentaCorriente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaCorrienteMapper {
    CuentaCorrienteResponseDTO toResponseDTO(CuentaCorriente cuentaCorriente);
}
