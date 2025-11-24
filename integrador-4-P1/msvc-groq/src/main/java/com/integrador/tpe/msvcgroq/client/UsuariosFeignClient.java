package com.integrador.tpe.msvcgroq.client;

import com.integrador.tpe.msvcgroq.dto.interservice.ReporteConsumoPersonal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@FeignClient(name = "msvc-usuarios", path = "/api/usuarios")
public interface UsuariosFeignClient {
    @GetMapping("/{id}/reporte-chat/consumo-servicio")
    ReporteConsumoPersonal obtenerReporteConsumoPersonal(
            @PathVariable Long id,
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            @RequestParam(required = false, defaultValue = "false") boolean incluyeRelaciones
    );
}
