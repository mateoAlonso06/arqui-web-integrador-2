package com.integrador.tpe.msvcusuarios.clients;

import com.integrador.tpe.msvcusuarios.dto.inteservice.Viaje;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-viajes", path = "api/viajes")
public interface ViajeFeignClient {

    @GetMapping("/usuarios/{idUsuario}")
    List<Viaje> obtenerViajesPorUsuario(@PathVariable Long id);
}
