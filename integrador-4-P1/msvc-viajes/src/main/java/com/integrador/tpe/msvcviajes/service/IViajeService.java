package com.integrador.tpe.msvcviajes.service;

import com.integrador.tpe.msvcviajes.dto.interservice.request.UsuarioBusquedaDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.request.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteConsumoPersonalServicio;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteUsoMonopatines;
import com.integrador.tpe.msvcviajes.dto.interservice.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IViajeService {
    ViajeResponseDTO iniciarViaje(ViajeRequestDTO viajeRequestDTO);

    ViajeResponseDTO getViajeById(Long idViaje);

    void deleteViajeById(Long idViaje);

    void finalizarViaje(Long idViaje, ViajeRequestDTO viajeRequestDTO);

    Page<ViajeResponseDTO> getAllViajes(Pageable pageable, LocalDateTime fecha);

    List<ViajeReporteResponseDTO> findAllViajesHechosPorAnioConCantidadViajesX(ViajeReporteRequestDTO viajeReporteRequestDTO);

    List<UsuarioResponseDTO> findTopUsuariosPorUso(UsuarioBusquedaDTO usuarioBusquedaDTO);

    List<ReporteUsoMonopatines> generarReporteUsoMonopatines(boolean incluyePausa);

    ReporteConsumoPersonalServicio generarReporteConsumoPersonalServicio(Long idUsuario, LocalDateTime periodoInicio, LocalDateTime periodoFin);
}
