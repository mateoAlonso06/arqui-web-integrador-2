package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.response.CuentaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", contextId = "cuentasId", path = "api/cuentas")
public interface CuentaFeignClient {
    @GetMapping("/{id}/estado")
    boolean isCuentaHabilitada(@PathVariable Long id);

    @GetMapping("/{id}/tipo")
    String getTipoCuenta(@PathVariable Long id);

    @GetMapping("/{id}")
    CuentaResponseDTO obtenerCuentaPorId(@PathVariable Long id);
}
