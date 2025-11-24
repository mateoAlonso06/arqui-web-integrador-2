package com.integrador.tpe.msvctarifas.controller;

import com.integrador.tpe.msvctarifas.dto.request.TarifaRequestDTO;
import com.integrador.tpe.msvctarifas.dto.response.EnviarTarifas;
import com.integrador.tpe.msvctarifas.dto.response.TarifaResponseDTO;
import com.integrador.tpe.msvctarifas.service.ITarifaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/tarifas")
public class    TarifaController {
    private final ITarifaService tarifaService;

    @GetMapping("/montos")
    public ResponseEntity<EnviarTarifas> getMontosTarifa() {
        EnviarTarifas tarifas = tarifaService.getMontos();
        return ResponseEntity.ok().body(tarifas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaResponseDTO> getTarifaById(@PathVariable @NotNull @Positive Long id) {
        TarifaResponseDTO tarifa = tarifaService.getTarifaById(id);
        return ResponseEntity.ok().body(tarifa);
    }

    @GetMapping
    public ResponseEntity<Page<TarifaResponseDTO>> getAllTarifas(Pageable pageable) {
        Page<TarifaResponseDTO> tarifas = tarifaService.getAllTarifas(pageable);
        return ResponseEntity.ok().body(tarifas);
    }

    @PostMapping
    public ResponseEntity<TarifaResponseDTO> createTarifa(@RequestBody @Valid TarifaRequestDTO tarifaRequestDTO) {
        TarifaResponseDTO tarifa = tarifaService.createTarifa(tarifaRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarifa.id())
                .toUri();

        return ResponseEntity.created(location).body(tarifa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable @Positive @NotNull Long id) {
        tarifaService.deleteTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/admin/{idAdmin}")
    public ResponseEntity<TarifaResponseDTO> updateTarifa(@PathVariable @Positive @NotNull Long id,
                                                          @RequestBody @Valid TarifaRequestDTO tarifaRequestDTO,
                                                          @PathVariable @NotNull @Positive Long idAdmin) {
        TarifaResponseDTO tarifa = tarifaService.updateTarifa(id, tarifaRequestDTO, idAdmin);
        return ResponseEntity.ok().body(tarifa);
    }
}
