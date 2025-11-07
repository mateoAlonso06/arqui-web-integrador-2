package com.integrador.tpe.msvfacturacion.mapper;

import com.integrador.tpe.msvfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvfacturacion.entity.CuentaCorriente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaCorrienteMapper {
    CuentaCorrienteResponseDTO toResponseDTO(CuentaCorriente cuentaCorriente);
}
