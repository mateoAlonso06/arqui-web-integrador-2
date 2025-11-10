package com.integrador.tpe.msvcreportes.service;

import com.integrador.tpe.msvcreportes.dto.TransaccionFiltroDTO;
import com.integrador.tpe.msvcreportes.dto.UsuarioReporteResponseDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteResponseDTO;

import java.util.List;
/**a.Como administrador quiero poder generar un reporte de uso de monopatines por kilómetros
para establecer si un monopatín requiere de mantenimiento. Este reporte debe poder
configurarse para incluir (o no) los tiempos de pausa. CONSULTAR

e. Como administrador quiero ver los usuarios que más utilizan los monopatines, filtrado por
período y por tipo de usuario.
*/
public interface IReportesService {
    List<ViajeReporteResponseDTO> generarReporteViajesMonopatines(ViajeReporteRequestDTO viajeReporteRequestDTO);

    String obtenerTotalFacturadoEnRangoDeMeses(TransaccionFiltroDTO transaccionFiltroDTO);

    List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso();
}
