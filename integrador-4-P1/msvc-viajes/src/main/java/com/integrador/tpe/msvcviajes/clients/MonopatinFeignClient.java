package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.MonopatinResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-flota", path = "/monopatines")
public interface MonopatinFeignClient {
    @GetMapping("/{idMonopatin}")
    MonopatinResponseDTO getMonopatinById(@PathVariable Long idMonopatin);

    @GetMapping("/{id}/estado")
    void actualizarEstadoMonopatin(@PathVariable Long id, @RequestBody String estado);

    @PatchMapping("/{idMonopatin}/recorrido")
    void actualizarRecorridoMonopatin(@PathVariable Long idMonopatin, @RequestBody Double kmRecorridos);
}
