package com.integrador.tpe.msvcreportes.clients;

import com.integrador.tpe.msvcreportes.dto.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "msvc-viajes", path = "api/viajes")
public interface ViajeFeignClient {
    @GetMapping("/monopatines/reporte")
    List<ViajeReporteResponseDTO> obtenerReporteDeViajesPorMonopatin(@RequestBody ViajeReporteRequestDTO viajeReporteRequestDTO);
}
