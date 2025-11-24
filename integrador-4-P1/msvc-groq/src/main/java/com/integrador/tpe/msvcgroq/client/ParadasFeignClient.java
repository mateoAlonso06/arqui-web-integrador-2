package com.integrador.tpe.msvcgroq.client;

import com.integrador.tpe.msvcgroq.dto.interservice.MonopatinResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-flota", path = "/api/paradas")
public interface ParadasFeignClient {
    @GetMapping("/{idParada}/monopatines")
    List<MonopatinResponseDTO> getMonopatinesEnParada(@PathVariable String idParada);
}
