package com.integrador.tpe.msvcviajes.controller;

import com.integrador.tpe.msvcviajes.service.IViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/viajes")
public class ViajeController {
    private final IViajeService viajeService;

//    @GetMapping("/usuarios/{id")
//    public ResponseEntity<List<ViajeResponseDTO>> getViajesByUsuario(@PathVariable Long idUsuario) {
//        // la validacion de que la persona exista la hago en el otro microservicio
//        List<ViajeResponseDTO> viajes = viajeService.obtenerViajesPorUsuario(idUsuario);
//        return ResponseEntity.ok().body(viajes);
//    }

    @PostMapping
    public ResponseEntity<Void> comenzarViaje() {

    }
}
