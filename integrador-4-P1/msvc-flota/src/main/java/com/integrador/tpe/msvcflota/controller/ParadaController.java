package com.integrador.tpe.msvcflota.controller;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.service.IParadaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/paradas")
public class ParadaController {
    private final IParadaService paradaService;

    @GetMapping("/{idParada}/monopatines")
    public ResponseEntity<List<MonopatinResponseDTO>> getMonopatinesEnParada(@PathVariable @NotBlank String idParada) {
        List<MonopatinResponseDTO> monopatines = paradaService.getMonopatinesEnParada(idParada);
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping
    public ResponseEntity<Page<ParadaResponseDTO>> getParadas(Pageable pageable) {
        Page<ParadaResponseDTO> paradas = paradaService.getAllParadas(pageable);
        return ResponseEntity.ok(paradas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParadaResponseDTO> getParadaById(@PathVariable @NotBlank String id) {
        ParadaResponseDTO paradaResponseDTO = paradaService.getParadaById(id);
        return ResponseEntity.ok().body(paradaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParada(@PathVariable @NotBlank String id) {
        paradaService.deleteParada(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ParadaResponseDTO> createParada(@RequestBody @Valid ParadaRequestDTO paradaRequestDTO) {
        ParadaResponseDTO paradaCreada = paradaService.addParada(paradaRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(paradaCreada.id())
                .toUri();

        return ResponseEntity.created(location).body(paradaCreada);
    }
}
