package com.integrador.tpe.msvcflota.controller;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.service.IParadaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
@RequestMapping("/api/paradas")
public class ParadaController {
    private final IParadaService paradaService;

    @GetMapping("/{idParada}/monopatines")
    public ResponseEntity<Page<MonopatinResponseDTO>> getMonopatinesEnParada(@PathVariable ObjectId idParada, Pageable pageable) {
        Page<MonopatinResponseDTO> monopatines = paradaService.getMonopatinesEnParada(idParada, pageable);
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> getParadaById(@PathVariable @Positive @NotNull Long id) {
        ParadaResponseDTO paradaResponseDTO = paradaService.getParadaById(id);
        return ResponseEntity.ok().body(paradaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable @Positive @NotNull Long id) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ParadaResponseDTO> createParada(@RequestBody @Valid ParadaRequestDTO paradaRequestDTO) {
        ParadaResponseDTO paradaCreada = paradaService.addParada(paradaRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/paradas/{id}")
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(paradaCreada);
    }
}
