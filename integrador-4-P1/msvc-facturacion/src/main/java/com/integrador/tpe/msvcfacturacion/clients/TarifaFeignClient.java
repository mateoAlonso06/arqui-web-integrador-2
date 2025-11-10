package com.integrador.tpe.msvcfacturacion.clients;

import com.integrador.tpe.msvcfacturacion.dto.interservice.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-tarifas", path = "api/tarifas")
public interface TarifaFeignClient {
    @GetMapping("/montos")
    TarifaDTO getTarifas();
}
