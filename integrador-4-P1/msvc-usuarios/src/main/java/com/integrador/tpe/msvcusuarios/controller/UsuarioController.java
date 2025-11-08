package com.integrador.tpe.msvcusuarios.controller;

import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioUpdateDTO;
import com.integrador.tpe.msvcusuarios.dto.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcusuarios.service.IUsuarioService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/usuarios")
public class UsuarioController {
    private final IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> addUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        UsuarioResponseDTO createdUsuario = usuarioService.createUsuario(usuarioRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUsuario.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(Long id) {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsuarios(Pageable pageable) {
        Page<UsuarioResponseDTO> usuariosPage = usuarioService.getAllUsuarios(pageable);
        return ResponseEntity.ok(usuariosPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<UsuarioResponseDTO> updatePatchUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioResponseDTO updatedUsuario = usuarioService.updatePatchUsuario(id, usuarioUpdateDTO);
        return ResponseEntity.ok(updatedUsuario);
    }
}
