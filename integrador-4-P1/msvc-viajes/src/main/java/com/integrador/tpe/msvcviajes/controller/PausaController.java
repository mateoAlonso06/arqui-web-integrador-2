package com.integrador.tpe.msvcviajes.controller;

import com.integrador.tpe.msvcviajes.service.IPausaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pausas")
public class PausaController {
    private final IPausaService pausaService;

    @PostMapping("/viajes/{idViaje}/pausar")
    public ResponseEntity<Void> pausarViaje(@PathVariable Long idViaje) {
        pausaService.pausarViaje(idViaje);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{idPausa}/reanudar/viajes/{idViaje}")
    public ResponseEntity<Void> reanudarViaje(@PathVariable Long idPausa, @PathVariable Long idViaje) {
        pausaService.renaudarViaje(idPausa, idViaje);
        return ResponseEntity.ok().build();
    }
}
