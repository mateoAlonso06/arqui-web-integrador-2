package com.integrador.tpe.msvcusuarios.controller;

import com.integrador.tpe.msvcusuarios.dto.request.CuentaRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.response.CuentaResponseDTO;
import com.integrador.tpe.msvcusuarios.service.ICuentaService;
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

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/cuentas")
public class CuentaController {
    private final ICuentaService cuentaService;

    @GetMapping
    public ResponseEntity<Page<CuentaResponseDTO>> getAllCuentas(
            @RequestParam(required = false, defaultValue = "true") Boolean habilitado, Pageable pageable) {
        Page<CuentaResponseDTO> cuentas = cuentaService.getAllCuentas(habilitado, pageable);

        return ResponseEntity.ok().body(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> getCuentaById(@PathVariable @Positive @NotNull Long id) {
        CuentaResponseDTO cuenta = cuentaService.getCuentaById(id);
        return ResponseEntity.ok().body(cuenta);
    }

    //throws ConstraintViolationException
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuentaById(@PathVariable @Positive @NotNull Long id) {
        cuentaService.deleteCuentaById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> addCuenta(@RequestBody @Valid CuentaRequestDTO cuentaCreateDTO) {
        CuentaResponseDTO createdCuenta = cuentaService.createCuenta(cuentaCreateDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/cuentas/{id}")
                .buildAndExpand(createdCuenta.id())
                .toUri();

        return ResponseEntity.created(location).body(createdCuenta);
    }

    @PostMapping("/{idCuenta}/usuarios/{idUsuario}")
    public ResponseEntity<Void> asignarCuentaAUsuario(@PathVariable @Positive @NotNull Long idCuenta,
                                                      @PathVariable @Positive @NotNull Long idUsuario) {
        cuentaService.asociarUsuarioACuenta(idCuenta, idUsuario); // no retorna nada
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idCuenta}/usuarios/{idUsuario}")
    public ResponseEntity<Void> desasignarCuentaAUsuario(@PathVariable @Positive @NotNull Long idCuenta,
                                                         @PathVariable @Positive @NotNull Long idUsuario) {
        cuentaService.removerUsuarioDeCuenta(idCuenta, idUsuario); // no retorna nada
        return ResponseEntity.ok().build();
    }

    // endpoint protegido
    @PatchMapping("/{id}/habilitar")
    public ResponseEntity<CuentaResponseDTO> habilitarCuenta(@PathVariable @Positive @NotNull Long id) {
        CuentaResponseDTO cuentaHabilitada = cuentaService.habilitarCuenta(id);
        return ResponseEntity.ok().body(cuentaHabilitada);
    }

    // endpoint protegido
    @PatchMapping("/{id}/deshabilitar")
    public ResponseEntity<CuentaResponseDTO> deshabilitarCuenta(@PathVariable @Positive @NotNull Long id) {
        CuentaResponseDTO cuentaDeshabilitada = cuentaService.deshabilitarCuenta(id);
        return ResponseEntity.ok().body(cuentaDeshabilitada);
    }
}
