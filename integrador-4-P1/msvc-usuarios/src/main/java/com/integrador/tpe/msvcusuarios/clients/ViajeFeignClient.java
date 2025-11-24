package com.integrador.tpe.msvcusuarios.clients;

import com.integrador.tpe.msvcusuarios.dto.response.ReporteConsumoPersonalServicio;
import com.integrador.tpe.msvcusuarios.dto.response.ReporteUsoMonopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "msvc-viajes", path = "api/viajes")
public interface ViajeFeignClient {
    @GetMapping("/monopatines/uso/reporte")
    List<ReporteUsoMonopatin> generarReporteUsoMonopatines(@RequestParam(required = false, defaultValue = "false") boolean incluyePausa);

    @GetMapping("/servicios/consumo/{idUsuario}/reporte")
    ReporteConsumoPersonalServicio generarReporteConsumoPersonalServicio(
            @PathVariable Long idUsuario,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin
    );
}
