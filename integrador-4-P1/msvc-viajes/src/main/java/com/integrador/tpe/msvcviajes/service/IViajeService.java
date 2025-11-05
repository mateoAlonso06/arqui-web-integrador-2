package com.integrador.tpe.msvcviajes.service;

import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IViajeService {
    ViajeResponseDTO iniciarViaje(Long idUsuario, Long idMonopatin, Long idCuenta);

    ViajeResponseDTO getViajeById(Long idViaje);

    void deleteViajeById(Long idViaje);

    // Puede filtrar los viajes por fecha de inicio
    Page<ViajeResponseDTO> getAllViajes(Pageable pageable, LocalDateTime fechaInicio);
}
