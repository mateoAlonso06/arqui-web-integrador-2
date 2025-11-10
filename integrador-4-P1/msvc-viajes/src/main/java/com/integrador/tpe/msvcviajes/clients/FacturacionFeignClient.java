package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.response.InformacionViaje;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-facturacion", path = "api/facturacion")
public interface FacturacionFeignClient {
    @GetMapping("/cuentas/{idCuenta}/saldo")
    boolean tieneDeudasPendientes(@PathVariable Long idCuenta);

    @GetMapping("/cuentas/{idCuenta}/servicio-activo")
    boolean activoServicio(@PathVariable Long idCuenta);

    @PutMapping("/usuarios/{idUsuario}/saldos/debitar")
    void generarFactura(@PathVariable Long idUsuario, @RequestBody @Valid InformacionViaje informacionViaje);
}
