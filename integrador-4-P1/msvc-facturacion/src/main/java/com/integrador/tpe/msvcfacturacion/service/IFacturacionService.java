package com.integrador.tpe.msvcfacturacion.service;

import com.integrador.tpe.msvcfacturacion.dto.request.CargaSaldoDTO;
import com.integrador.tpe.msvcfacturacion.dto.request.InformacionViaje;
import com.integrador.tpe.msvcfacturacion.dto.response.CuentaCorrienteResponseDTO;

import java.util.List;

public interface IFacturacionService {
    CuentaCorrienteResponseDTO cargarSaldo(Long cuentaId, CargaSaldoDTO cargaSaldoDTO);

    void debitarViaje(Long idUsuario, InformacionViaje informacionViaje);

    boolean tieneDeudasPendientes(Long idCuenta);

    boolean activoServicio(Long idCuenta);

    List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso();
}
