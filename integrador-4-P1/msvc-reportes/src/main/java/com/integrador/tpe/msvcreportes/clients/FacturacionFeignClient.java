package com.integrador.tpe.msvcreportes.clients;

import com.integrador.tpe.msvcreportes.dto.TransaccionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "msvc-facturacion", path = "api/transacciones")
public interface FacturacionFeignClient {
    @GetMapping("/facturado")
    List<TransaccionResponseDTO> getAllTransacciones(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin);
}
