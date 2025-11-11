package com.integrador.tpe.msvcfacturacion.controller;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.service.ITransaccionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<TransaccionResponseDTO>> getAllTransacciones(@ModelAttribute TransaccionFiltroDTO transaccionFiltroDTO) {
        List<TransaccionResponseDTO> transacciones = transaccionService.getTransacciones(transaccionFiltroDTO);
        return ResponseEntity.ok().body(transacciones);
    }
}
