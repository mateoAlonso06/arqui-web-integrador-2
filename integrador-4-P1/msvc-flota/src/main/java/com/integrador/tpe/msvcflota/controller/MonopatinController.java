package com.integrador.tpe.msvcflota.controller;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.service.IMonopatinService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/monopatines")
public class MonopatinController {
    private final IMonopatinService monopatinService;

    @PostMapping
    public ResponseEntity<MonopatinResponseDTO> crearMonopatin(@RequestBody MonopatinRequestDTO monopatinRequestDTO) {
        MonopatinResponseDTO responseDTO = monopatinService.addMonopatin(monopatinRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMonopatin(@PathVariable ObjectId id) {
        monopatinService.deleteMonopatin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> obtenerMonopatinPorId(@PathVariable ObjectId id) {
        MonopatinResponseDTO responseDTO = monopatinService.getMonopatinById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MonopatinResponseDTO>> obtenerTodosLosMonopatines(Pageable pageable) {
        Page<MonopatinResponseDTO> monopatines = monopatinService.getAllMonopatines(pageable);
        return ResponseEntity.ok(monopatines);
    }
}
