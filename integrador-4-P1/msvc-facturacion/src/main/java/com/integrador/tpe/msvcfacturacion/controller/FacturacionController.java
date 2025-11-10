package com.integrador.tpe.msvcfacturacion.controller;

import com.integrador.tpe.msvcfacturacion.dto.request.CargaSaldoDTO;
import com.integrador.tpe.msvcfacturacion.dto.request.InformacionViaje;
import com.integrador.tpe.msvcfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvcfacturacion.service.IFacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/facturacion")
public class FacturacionController {
    private final IFacturacionService facturacionService;

    @GetMapping("/cuentas/{idCuenta}/servicio-activo")
    public ResponseEntity<Boolean> activoServicio(@PathVariable Long idCuenta) {
        boolean servicioActivo = facturacionService.activoServicio(idCuenta);
        return ResponseEntity.ok().body(servicioActivo);
    }

    @PutMapping("/usuarios/{id}/saldos/cargar")
    public ResponseEntity<CuentaCorrienteResponseDTO> cargarSaldo(@PathVariable Long id, @RequestBody CargaSaldoDTO cargaSaldoDTO) {
        CuentaCorrienteResponseDTO cuentaCorriente = facturacionService.cargarSaldo(id, cargaSaldoDTO);
        return ResponseEntity.ok(cuentaCorriente);
    }

    @PutMapping("/usuarios/{id}/saldos/debitar")
    public ResponseEntity<Void> debitarSaldo(@PathVariable Long id, @RequestBody InformacionViaje informacionViaje) {
        facturacionService.debitarViaje(id, informacionViaje);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cuentas/{idCuenta}/saldo")
    public ResponseEntity<Boolean> tieneDeudasPendientes(@PathVariable Long idCuenta) {
        boolean tieneDeudas = facturacionService.tieneDeudasPendientes(idCuenta);
        return ResponseEntity.ok(tieneDeudas);
    }
}
