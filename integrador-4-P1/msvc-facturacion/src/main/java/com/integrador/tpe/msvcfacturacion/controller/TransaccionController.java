package com.integrador.tpe.msvcfacturacion.controller;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.service.ITransaccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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

    @GetMapping
    public ResponseEntity<Page<TransaccionResponseDTO>> getAllTransacciones(@RequestBody(required = false) TransaccionFiltroDTO transaccionFiltroDTO, Pageable pageable) {
        Page<TransaccionResponseDTO> transacciones = transaccionService.getTransacciones(transaccionFiltroDTO, pageable);
        return ResponseEntity.ok().body(transacciones);
    }
}
