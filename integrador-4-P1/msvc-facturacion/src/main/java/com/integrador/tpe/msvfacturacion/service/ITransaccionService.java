package com.integrador.tpe.msvfacturacion.service;

import com.integrador.tpe.msvfacturacion.dto.response.TransaccionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ITransaccionService {
    TransaccionResponseDTO getTransaccionById(Long idTransaccion);

    void eliminarTransaccionById(Long idTransaccion);

    Page<TransaccionResponseDTO> getTransacciones(LocalDate fechaInicio, LocalDate fechaFin, Pageable pageable);
}
