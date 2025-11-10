package com.integrador.tpe.msvcflota.controller;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import com.integrador.tpe.msvcflota.service.IMonopatinService;
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

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/monopatines")
public class MonopatinController {
    private final IMonopatinService monopatinService;

    @PutMapping("/{idMonopatin}/ubicacion")
    public ResponseEntity<Void> actualizarUbicacionMonopatin(@PathVariable @NotBlank String idMonopatin, @RequestBody UbicacionGPS nuevaUbicacion) {
        monopatinService.actualizarUbicacionMonopatin(idMonopatin, nuevaUbicacion);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idMonopatin}/estado")
    public ResponseEntity<Void> actualizarEstadoMonopatin(@PathVariable @NotBlank String idMonopatin, @RequestBody @NotBlank String estado) {
        monopatinService.actualizarEstadoMonopatin(idMonopatin, estado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{IdMonopatin}/recorrido")
    public ResponseEntity<Void> actualizarRecorridoMonopatin(@PathVariable @NotBlank String IdMonopatin, @RequestBody Double kmRecorridos) {
        monopatinService.actualizarRecorridoMonopatin(IdMonopatin, kmRecorridos);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<MonopatinResponseDTO> crearMonopatin(@RequestBody @Valid MonopatinRequestDTO monopatinRequestDTO) {
        MonopatinResponseDTO responseDTO = monopatinService.addMonopatin(monopatinRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMonopatin(@PathVariable @NotBlank String id) {
        monopatinService.deleteMonopatin(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> obtenerMonopatinPorId(@PathVariable @NotBlank String id) {
        MonopatinResponseDTO responseDTO = monopatinService.getMonopatinById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<MonopatinResponseDTO>> obtenerTodosLosMonopatines(Pageable pageable) {
        Page<MonopatinResponseDTO> monopatines = monopatinService.getAllMonopatines(pageable);
        return ResponseEntity.ok(monopatines);
    }
}