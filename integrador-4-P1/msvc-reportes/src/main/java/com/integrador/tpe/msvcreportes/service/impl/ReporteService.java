package com.integrador.tpe.msvcreportes.service.impl;

import com.integrador.tpe.msvcreportes.clients.FacturacionFeignClient;
import com.integrador.tpe.msvcreportes.clients.UsuarioFeignClient;
import com.integrador.tpe.msvcreportes.clients.ViajeFeignClient;
import com.integrador.tpe.msvcreportes.dto.*;
import com.integrador.tpe.msvcreportes.model.TipoCuenta;
import com.integrador.tpe.msvcreportes.service.IReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService implements IReportesService {
    private final ViajeFeignClient viajeFeignClient;
    private final FacturacionFeignClient facturacionFeignClient;
    private final UsuarioFeignClient usuarioFeignClient;

    @Override
    public List<ViajeReporteResponseDTO> generarReporteViajesMonopatines(ViajeReporteRequestDTO viajeReporteRequestDTO, Long idAdmin) {
        if (!esAdministrador(idAdmin)) {
            throw new RuntimeException("Acceso denegado: El usuario no es administrador.");
        }

        Integer cantidadViajes = viajeReporteRequestDTO.cantidadViajes();
        Integer anio = viajeReporteRequestDTO.anio();

        return viajeFeignClient.obtenerReporteDeViajesPorMonopatin(cantidadViajes, anio);
    }

    @Override
    public ReporteFacturacion obtenerTotalFacturadoEnRangoDeMeses(TransaccionFiltroDTO transaccionFiltroDTO, Long idAdmin) {
        if (!esAdministrador(idAdmin)) {
            throw new RuntimeException("Acceso denegado: El usuario no es administrador.");
        }

        LocalDateTime fechaInicio = transaccionFiltroDTO.fechaInicio();
        LocalDateTime fechaFin = transaccionFiltroDTO.fechaFin();

        List<TransaccionResponseDTO> transacciones = facturacionFeignClient.getAllTransacciones(fechaInicio, fechaFin);
        BigDecimal totalFacturado = BigDecimal.ZERO;

        for (TransaccionResponseDTO transaccion : transacciones)
            totalFacturado = totalFacturado.add(transaccion.monto());

        return new ReporteFacturacion(
                totalFacturado,
                transacciones.size(),
                transaccionFiltroDTO.fechaInicio(),
                transaccionFiltroDTO.fechaFin()
        );
    }

    @Override
    public List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso(UsuarioBusquedaDTO usuarioBusquedaDTO, Long idAdmin) {
        if (!esAdministrador(idAdmin))
            throw new RuntimeException("Acceso denegado: El usuario no es administrador.");

        LocalDateTime fechaInicio = usuarioBusquedaDTO.fechaInicio();
        LocalDateTime fechaFin = usuarioBusquedaDTO.fechaFin();
        TipoCuenta tipoCuenta = usuarioBusquedaDTO.tipoCuenta();

        return viajeFeignClient.obtenerTopUsuariosPorUso(fechaInicio, fechaFin, tipoCuenta);
    }

    private boolean esAdministrador(Long idAdmin) {
        try {
            boolean resultado = usuarioFeignClient.esAdministrador(idAdmin);
            return resultado;
        }catch(Exception e) {
            throw new RuntimeException("Error al verificar el rol del usuario: " + e.getMessage());
        }
    }
}
