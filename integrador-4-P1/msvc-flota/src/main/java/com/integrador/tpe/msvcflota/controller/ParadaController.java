package com.integrador.tpe.msvcflota.controller;

import com.integrador.tpe.msvcflota.dto.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.service.IParadaService;
import com.integrador.tpe.msvcflota.service.impl.ParadaService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/paradas")
public class ParadaController {
    private final IParadaService paradaService;

    @GetMapping("/{idParada}/monopatines")
    public ResponseEntity<Page<MonopatinResponseDTO>> getMonopatinesEnParada(@PathVariable ObjectId idParada) {
        Page<MonopatinResponseDTO> monopatines = paradaService.obtenerMonopatines(idParada);
        return ResponseEntity.ok(monopatines);
    }
}
