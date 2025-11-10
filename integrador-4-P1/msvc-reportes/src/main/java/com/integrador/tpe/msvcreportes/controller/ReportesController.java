package com.integrador.tpe.msvcreportes.controller;

import com.integrador.tpe.msvcreportes.dto.*;
import com.integrador.tpe.msvcreportes.service.IReportesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/reportes")
public class ReportesController {
    private final IReportesService reportesService;

    @GetMapping("/monopatines/reporte")
    public ResponseEntity<List<ViajeReporteResponseDTO>> obtenerReporteViajesMonopatines(@RequestBody @Valid ViajeReporteRequestDTO viajeReporteRequestDTO) {
        List<ViajeReporteResponseDTO> reporte = reportesService.generarReporteViajesMonopatines(viajeReporteRequestDTO);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/facturacion/rango-meses")
    public ResponseEntity<String> obtenerTotalFacturadoEnRangoDeMeses(@RequestBody TransaccionFiltroDTO transaccionFiltroDTO) {
        String resultado = reportesService.obtenerTotalFacturadoEnRangoDeMeses(transaccionFiltroDTO);
        return ResponseEntity.ok(resultado);
    }


    @GetMapping("/usuarios/top-usuarios")
    public ResponseEntity<List<UsuarioReporteResponseDTO>> obtenerTopUsuariosPorUso(@RequestBody @Valid UsuarioFiltroDTO usuarioFiltroDTO) {
        List<UsuarioReporteResponseDTO> topUsuarios = reportesService.obtenerTopUsuariosPorUso();
        return ResponseEntity.ok(topUsuarios);
    }
}
