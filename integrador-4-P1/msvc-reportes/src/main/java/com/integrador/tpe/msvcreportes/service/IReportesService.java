package com.integrador.tpe.msvcreportes.service;

import com.integrador.tpe.msvcreportes.dto.*;

import java.util.List;

public interface IReportesService {
    List<ViajeReporteResponseDTO> generarReporteViajesMonopatines(ViajeReporteRequestDTO viajeReporteRequestDTO, Long idAdmin);

    ReporteFacturacion obtenerTotalFacturadoEnRangoDeMeses(TransaccionFiltroDTO transaccionFiltroDTO, Long idAdmin);

    List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso(UsuarioBusquedaDTO usuarioBusquedaDTO, Long idAdmin);
}
