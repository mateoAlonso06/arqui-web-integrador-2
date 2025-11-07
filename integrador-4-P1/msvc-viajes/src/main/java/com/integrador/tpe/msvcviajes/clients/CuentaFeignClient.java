package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", path = "/cuentas")
public interface CuentaFeignClient {
    @GetMapping("/{id}/estado")
    boolean isCuentaHabilitada(Long id);

    @GetMapping("/{id}/tipo")
    String getTipoCuenta(@PathVariable Long id);
}
