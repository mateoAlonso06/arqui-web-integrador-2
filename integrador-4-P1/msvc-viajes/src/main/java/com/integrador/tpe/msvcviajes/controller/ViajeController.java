package com.integrador.tpe.msvcviajes.controller;

import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.service.IViajeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/viajes")
public class ViajeController {
    private final IViajeService viajeService;

    @PostMapping
    public ResponseEntity<ViajeResponseDTO> iniciarViaje(@RequestBody @Valid ViajeRequestDTO viajeRequestDTO) {
        ViajeResponseDTO viaje = viajeService.iniciarViaje(viajeRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(viaje.id())
                .toUri();

        return ResponseEntity.created(location).body(viaje);
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Void> finalizarViaje(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid ViajeRequestDTO viajeRequestDTO) {
        viajeService.finalizarViaje(id, viajeRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeResponseDTO> getViajeById(@PathVariable @NotNull @Positive Long id) {
        ViajeResponseDTO viaje = viajeService.getViajeById(id);
        return ResponseEntity.ok(viaje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViajeById(@PathVariable @NotNull @Positive Long id) {
        viajeService.deleteViajeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ViajeResponseDTO>> getAllViajes(
            Pageable pageable,
            @RequestParam(required = false) LocalDateTime fecha) {
        Page<ViajeResponseDTO> viajes = viajeService.getAllViajes(pageable, fecha);
        return ResponseEntity.ok(viajes);
    }
}
