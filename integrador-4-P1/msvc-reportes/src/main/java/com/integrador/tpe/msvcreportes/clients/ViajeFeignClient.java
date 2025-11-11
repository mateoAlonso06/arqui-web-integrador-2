package com.integrador.tpe.msvcreportes.clients;

import com.integrador.tpe.msvcreportes.dto.UsuarioReporteResponseDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "msvc-viajes", path = "api/viajes")
public interface ViajeFeignClient {
    @GetMapping("/monopatines/reporte/cantidad-viajes")
    List<ViajeReporteResponseDTO> obtenerReporteDeViajesPorMonopatin(@RequestParam Integer cantidadViajes, @RequestParam Integer anio);

    @GetMapping("/usuarios/reporte/usuarios-top-uso")
    List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso(@RequestParam LocalDateTime fechaInicio,
                                                             @RequestParam LocalDateTime fechaFin,
                                                             @RequestParam String tipoCuenta);
}
