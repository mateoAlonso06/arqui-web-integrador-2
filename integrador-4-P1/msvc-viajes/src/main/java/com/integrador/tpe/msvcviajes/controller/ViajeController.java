package com.integrador.tpe.msvcviajes.controller;

import com.integrador.tpe.msvcviajes.dto.interservice.request.FechasFiltroDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.request.TipoCuenta;
import com.integrador.tpe.msvcviajes.dto.interservice.request.UsuarioBusquedaDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.request.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteConsumoPersonalServicio;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteUsoMonopatines;
import com.integrador.tpe.msvcviajes.dto.interservice.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.service.IViajeService;
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
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/viajes")
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

    @GetMapping("/monopatines/reporte/cantidad-viajes")
    public ResponseEntity<List<ViajeReporteResponseDTO>> getAllViajesHechosPorAnioConCantidadViajesX(@ModelAttribute ViajeReporteRequestDTO viajeReporteRequestDTO) {
        List<ViajeReporteResponseDTO> viajes = viajeService.findAllViajesHechosPorAnioConCantidadViajesX(viajeReporteRequestDTO);
        return ResponseEntity.ok().body(viajes);
    }

    @GetMapping("/usuarios/reporte/usuarios-top-uso")
    public ResponseEntity<List<UsuarioResponseDTO>> getTopUsuariosPorUso(@RequestParam(required = true) LocalDateTime fechaInicio,
                                                                         @RequestParam(required = true) LocalDateTime fechaFin,
                                                                         @RequestParam(required = true) TipoCuenta tipoCuenta) {
        List<UsuarioResponseDTO> usuarios = viajeService.findTopUsuariosPorUso(new UsuarioBusquedaDTO(fechaInicio, fechaFin, tipoCuenta));
        return ResponseEntity.ok().body(usuarios);
    }

    @GetMapping("/monopatines/uso/reporte") // Lo usa usuarios // inciso A -> lo consume groq
    public ResponseEntity<List<ReporteUsoMonopatines>> generarReporteUsoMonopatines(@RequestParam boolean incluyePausa) {
        List<ReporteUsoMonopatines> reporte = viajeService.generarReporteUsoMonopatines(incluyePausa);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/servicios/consumo/{idUsuario}/reporte") // Lo usa usuarios // inciso H
    public ResponseEntity<ReporteConsumoPersonalServicio> generarReporteConsumoPersonalServicio(@PathVariable @NotNull @Positive Long idUsuario, @ModelAttribute @Valid FechasFiltroDTO fechasFiltro) {
        ReporteConsumoPersonalServicio reporte = viajeService.generarReporteConsumoPersonalServicio(idUsuario, fechasFiltro.fechaInicio(), fechasFiltro.fechaFin());
        return ResponseEntity.ok().body(reporte);
    }
}
