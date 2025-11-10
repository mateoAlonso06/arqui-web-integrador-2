package com.integrador.tpe.msvcusuarios.service;

import com.integrador.tpe.msvcusuarios.dto.request.CuentaRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.response.CuentaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICuentaService {
    Page<CuentaResponseDTO> getAllCuentas(Boolean habilitado, Pageable pageable);

    CuentaResponseDTO getCuentaById(Long id);

    void deleteCuentaById(Long id);

    CuentaResponseDTO createCuenta(CuentaRequestDTO cuentaCreateDTO);

    CuentaResponseDTO habilitarCuenta(Long id);

    CuentaResponseDTO deshabilitarCuenta(Long id);

    void asociarUsuarioACuenta(Long cuentaId, Long usuarioId);

    void removerUsuarioDeCuenta(Long idCuenta, Long idUsuario);

    boolean isCuentaHabilitada(Long id);

    String getTipoCuenta(Long id);
}
