package com.integrador.tpe.msvcfacturacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mercado-pago")
public class MercadoPagoController {
    // Simulamos el endpoint de cobro
    @PostMapping("/cobrar")
    public ResponseEntity<Map<String, Object>> simularCobro(@RequestBody Map<String, Object> datosPago) {
        Map<String, Object> respuesta = new HashMap<>();

        // Simulamos lógica básica para pruebas
        // Si envías un monto negativo o 0, falla.
        Double monto = Double.valueOf(datosPago.getOrDefault("monto", 0).toString());

        if (monto <= 0) {
            respuesta.put("estado", "RECHAZADO");
            respuesta.put("mensaje", "Fondos insuficientes o monto inválido");
            return ResponseEntity.badRequest().body(respuesta); // 400 Bad Request
        }

        respuesta.put("estado", "APROBADO");
        respuesta.put("id_transaccion", "MP-" + System.currentTimeMillis());
        respuesta.put("mensaje", "Pago procesado correctamente");

        return ResponseEntity.ok(respuesta);
    }
}
