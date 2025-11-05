package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "msvc-facturacion", path = "/facturacion")
public interface FacturacionFeignClient {
}
