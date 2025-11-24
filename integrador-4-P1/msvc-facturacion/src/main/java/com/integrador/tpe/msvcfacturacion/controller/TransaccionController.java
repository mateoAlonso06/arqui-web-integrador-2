package com.integrador.tpe.msvcfacturacion.controller;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.service.ITransaccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/transacciones")
public class TransaccionController {
    private final ITransaccionService transaccionService;

    @DeleteMapping("/{idTransaccion}")
    public ResponseEntity<Void> eliminarTransaccionById(@PathVariable Long idTransaccion) {
        transaccionService.eliminarTransaccionById(idTransaccion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idTransaccion}")
    public ResponseEntity<TransaccionResponseDTO> getTransaccionById(@PathVariable Long idTransaccion) {
        TransaccionResponseDTO transaccion = transaccionService.getTransaccionById(idTransaccion);
        return ResponseEntity.ok(transaccion);
    }

    @GetMapping("/facturado")
    public ResponseEntity<List<TransaccionResponseDTO>> getAllTransacciones(@RequestParam LocalDateTime fechaInicio, @RequestParam LocalDateTime fechaFin) {
        List<TransaccionResponseDTO> transacciones = transaccionService.getTransacciones(new TransaccionFiltroDTO(fechaInicio, fechaFin));
        return ResponseEntity.ok().body(transacciones);
    }
}
