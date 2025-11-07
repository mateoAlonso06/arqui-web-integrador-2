package com.integrador.tpe.msvfacturacion.client;

import com.integrador.tpe.msvfacturacion.dto.interservice.TarifaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-tarifa", path = "/api/tarifas")
public interface TarifaFeignClient {
    @GetMapping
    TarifaDTO getTarifas();
}
