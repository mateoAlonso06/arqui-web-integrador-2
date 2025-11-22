package com.integrador.tpe.msvcusuarios.service;

import com.integrador.tpe.msvcusuarios.dto.inteservice.UsuarioResponseValidated;
import com.integrador.tpe.msvcusuarios.dto.request.FechasFiltroDTO;
import com.integrador.tpe.msvcusuarios.dto.request.LoginRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.request.UsuarioUpdateDTO;
import com.integrador.tpe.msvcusuarios.dto.response.ReporteConsumoServicioCompleto;
import com.integrador.tpe.msvcusuarios.dto.response.ReporteUsoMonopatin;
import com.integrador.tpe.msvcusuarios.dto.response.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUsuarioService {
    UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO getUsuarioById(Long id);

    Page<UsuarioResponseDTO> getAllUsuarios(Pageable pageable);

    void deleteUsuario(Long id);

    UsuarioResponseDTO updatePatchUsuario(Long id, UsuarioUpdateDTO usuarioUpdateDTO);

    boolean existsUsuarioById(Long id);

    boolean estaAsociadoConCuenta(Long idUsuario, Long idCuenta);

    List<ReporteUsoMonopatin> generarReporteUsoMonopatines(Long idAdmin, boolean incluyePausa);

    boolean isAdmin(Long idUsuario);

    UsuarioResponseValidated validateCredentials(LoginRequestDTO loginRequestDTO);

    ReporteConsumoServicioCompleto obtenerReporteConsumo(Long idUsuario, FechasFiltroDTO fechasFiltroDTO, boolean incluyeRelaciones);
}
