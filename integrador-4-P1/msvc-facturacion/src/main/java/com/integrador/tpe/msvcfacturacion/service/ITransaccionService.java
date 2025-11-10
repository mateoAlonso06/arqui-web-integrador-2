package com.integrador.tpe.msvcfacturacion.service;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITransaccionService {
    TransaccionResponseDTO getTransaccionById(Long idTransaccion);

    void eliminarTransaccionById(Long idTransaccion);

    Page<TransaccionResponseDTO> getTransacciones(TransaccionFiltroDTO transaccionFiltroDTO, Pageable pageable);
}
