package com.integrador.tpe.msvcreportes.controller;

import com.integrador.tpe.msvcreportes.dto.*;
import com.integrador.tpe.msvcreportes.service.IReportesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reportes")
public class ReportesController {
    private final IReportesService reportesService;

    @GetMapping("/monopatines/admin/{idAdmin}") // INCISO C
    public ResponseEntity<List<ViajeReporteResponseDTO>> obtenerReporteViajesMonopatines(@ModelAttribute @Valid ViajeReporteRequestDTO viajeReporteRequestDTO, @PathVariable @Positive @NotNull Long idAdmin) {
        List<ViajeReporteResponseDTO> reporte = reportesService.generarReporteViajesMonopatines(viajeReporteRequestDTO, idAdmin);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/facturacion/rango-meses/admin/{idAdmin}") // INCISO D
    public ResponseEntity<ReporteFacturacion> obtenerTotalFacturadoEnRangoDeMeses(@ModelAttribute @Valid TransaccionFiltroDTO transaccionFiltroDTO, @PathVariable Long idAdmin) {
        ReporteFacturacion resultado = reportesService.obtenerTotalFacturadoEnRangoDeMeses(transaccionFiltroDTO, idAdmin);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/usuarios/top-usuarios/admin/{idAdmin}") // INCISO E
    public ResponseEntity<List<UsuarioReporteResponseDTO>> obtenerTopUsuariosPorUso(@ModelAttribute @Valid UsuarioBusquedaDTO usuarioBusquedaDTO, @PathVariable Long idAdmin) {
        List<UsuarioReporteResponseDTO> topUsuario = reportesService.obtenerTopUsuariosPorUso(usuarioBusquedaDTO, idAdmin);
        return ResponseEntity.ok(topUsuario);
    }
}
