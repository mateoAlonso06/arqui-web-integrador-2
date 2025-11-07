package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.ParadaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-paradas", path = "/api/paradas")
public interface ParadaFeignClient {
    @GetMapping("/{id}")
    ParadaResponseDTO getParadaById(@PathVariable Long id);
}
