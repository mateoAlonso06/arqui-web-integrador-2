package com.integrador.tpe.msvcusuarios.service;

import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioUpdateDTO;
import com.integrador.tpe.msvcusuarios.dto.response.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {
    UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO getUsuarioById(Long id);

    Page<UsuarioResponseDTO> getAllUsuarios(Pageable pageable);

    void deleteUsuario(Long id);

    UsuarioResponseDTO updatePatchUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO);

    boolean existsUsuarioById(Long id);

    boolean estaAsociadoConCuenta(Long idUsuario, Long idCuenta);
}
