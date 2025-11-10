package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.response.MonopatinResponseDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-flota", path = "api/monopatines")
public interface MonopatinFeignClient {
    @GetMapping("/{idMonopatin}")
    MonopatinResponseDTO getMonopatinById(@PathVariable String idMonopatin);

    @PutMapping("/{idMonopatin}/estado")
    void actualizarEstadoMonopatin(@PathVariable String idMonopatin, @RequestBody String estado);

    @PutMapping("/{idMonopatin}/recorrido")
    void actualizarRecorridoMonopatin(@PathVariable String idMonopatin, @RequestBody Double kmRecorridos);

    @PutMapping("/{idMonopatin}/ubicacion")
    void actualizarUbicacionMonopatin(@PathVariable String idMonopatin, @RequestBody UbicacionGPS nuevaUbicacion);
}
