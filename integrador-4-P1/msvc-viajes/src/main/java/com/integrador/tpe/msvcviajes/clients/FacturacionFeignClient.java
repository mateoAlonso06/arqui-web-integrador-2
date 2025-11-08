package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-facturacion", path = "/facturacion")
public interface FacturacionFeignClient {
    @GetMapping("/cuentas/{idCuenta}/saldo")
    boolean tieneDeudasPendientes(@PathVariable Long idCuenta);
}
