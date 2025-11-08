package com.integrador.tpe.msvcusuarios.service.impl;

import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioUpdateDTO;
import com.integrador.tpe.msvcusuarios.dto.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcusuarios.entity.Usuario;
import com.integrador.tpe.msvcusuarios.exception.UsuarioNotFoundException;
import com.integrador.tpe.msvcusuarios.mapper.UsuarioMapper;
import com.integrador.tpe.msvcusuarios.repository.IUsuarioRepository;
import com.integrador.tpe.msvcusuarios.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional
    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        if (usuarioRepository.existsByEmail(usuarioRequestDTO.email()))
            throw new IllegalArgumentException("Ya existe un usuario con email: " + usuarioRequestDTO.email());

        Usuario toSave = usuarioMapper.toEntity(usuarioRequestDTO);
        Usuario saved = usuarioRepository.save(toSave);

        return usuarioMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe un usuario con ID: " + id));

        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> getAllUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        if (!usuarioRepository.existsById(id))
            throw new IllegalArgumentException("No existe un usuario con ID: " + id);

        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updatePatchUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioToUpdate = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe usuario con ID: " + id));

        if (usuarioUpdateDTO.nombre() != null && !usuarioUpdateDTO.nombre().isBlank())
            usuarioToUpdate.setNombre(usuarioUpdateDTO.nombre());

        if (usuarioUpdateDTO.apellido() != null && !usuarioUpdateDTO.apellido().isBlank())
            usuarioToUpdate.setApellido(usuarioUpdateDTO.apellido());

        if (usuarioUpdateDTO.celular() != null && !usuarioUpdateDTO.celular().isBlank())
            usuarioToUpdate.setCelular(usuarioUpdateDTO.celular());

        if (usuarioUpdateDTO.email() != null && !usuarioUpdateDTO.email().isBlank()) {
            String email = usuarioUpdateDTO.email();

            if (!email.equals(usuarioToUpdate.getEmail()) && !usuarioRepository.existsByEmail(email))
                usuarioToUpdate.setEmail(email);
        }

        Usuario updated = usuarioRepository.save(usuarioToUpdate);
        return usuarioMapper.toResponseDTO(updated);
    }
}
