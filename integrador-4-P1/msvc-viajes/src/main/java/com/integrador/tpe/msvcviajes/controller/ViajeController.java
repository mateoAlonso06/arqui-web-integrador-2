package com.integrador.tpe.msvcviajes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/viajes")
@RequiredArgsConstructor
public class ViajeController {
    private final IViajeService viajeService;

    @GetMapping("/usuarios/{id")
    public ResponseEntity<List<ViajeResponseDTO>> getViajesByUsuario(@PathVariable Long idUsuario) {
        // la validacion de que la persona exista la hago en el otro microservicio
        List<ViajeResponseDTO> viajes = viajeService.obtenerViajesPorUsuario(idUsuario);
        return ResponseEntity.ok().body(viajes);
    }
}
