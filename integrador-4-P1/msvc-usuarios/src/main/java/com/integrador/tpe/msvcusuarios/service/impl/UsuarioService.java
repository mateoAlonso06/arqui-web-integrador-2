package com.integrador.tpe.msvcusuarios.service.impl;

import com.integrador.tpe.msvcusuarios.clients.ViajeFeignClient;
import com.integrador.tpe.msvcusuarios.dto.request.FechasFiltroDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioUpdateDTO;
import com.integrador.tpe.msvcusuarios.dto.response.*;
import com.integrador.tpe.msvcusuarios.entity.Role;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {
    private final IUsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final ViajeFeignClient viajeClient;

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

    @Override
    @Transactional(readOnly = true)
    public boolean existsUsuarioById(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean estaAsociadoConCuenta(Long idUsuario, Long idCuenta) {
        return usuarioRepository.existsByIdAndCuentas_Id(idUsuario, idCuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteUsoMonopatin> generarReporteUsoMonopatines(Long idAdmin, boolean incluyePausa) {
        // PARTE 2: desde JWT obtengo el id y verifico que con ese ID sea admin
        Usuario usuario = usuarioRepository.findById(idAdmin)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe un usuario con ID: " + idAdmin));

        if (!usuario.hasRole(Role.ADMIN))
            throw new SecurityException("El usuario con ID: " + idAdmin + " no tiene permisos de administrador.");

        return viajeClient.generarReporteUsoMonopatines(incluyePausa);
    }

    @Override
    public boolean isAdmin(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe un usuario con ID: " + idUsuario));

        return usuario.hasRole(Role.ADMIN);
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteConsumoServicioCompleto obtenerReporteConsumo(Long idUsuario, FechasFiltroDTO fechasFiltroDTO , boolean incluyeRelaciones) {
        Usuario usuarioPrincipal = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe un usuario con ID: " + idUsuario));

        ReporteConsumoServicioCompleto reporteCompleto = new ReporteConsumoServicioCompleto();
        ReporteConsumoPersonalServicio consumoPersonal = viajeClient.generarReporteConsumoPersonalServicio(idUsuario, fechasFiltroDTO);

        consumoPersonal.setIdUsuario(idUsuario);
        reporteCompleto.setUsuarioPrincipal(consumoPersonal);

        if (!incluyeRelaciones)
            return reporteCompleto;

        Set<Usuario> usuariosRelacionados = usuarioPrincipal.getCuentas().stream()
                .flatMap(cuenta -> cuenta.getUsuarios().stream())
                .filter(u -> !u.getId().equals(idUsuario))
                .collect(Collectors.toSet());

        List<ReporteConsumoPersonalServicio> consumosRelacionados = usuariosRelacionados.stream()
                .map(u -> viajeClient.generarReporteConsumoPersonalServicio(u.getId(), fechasFiltroDTO))
                .peek(r -> r.setIdUsuario(r.getIdUsuario()))
                .toList();

        reporteCompleto.setUsuariosRelacionados(consumosRelacionados);
        return reporteCompleto;
    }
}

