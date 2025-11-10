package com.integrador.tpe.msvcreportes.service.impl;

import com.integrador.tpe.msvcreportes.clients.MonopatinFeignClient;
import com.integrador.tpe.msvcreportes.clients.ViajeFeignClient;
import com.integrador.tpe.msvcreportes.dto.TransaccionFiltroDTO;
import com.integrador.tpe.msvcreportes.dto.UsuarioReporteResponseDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcreportes.dto.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcreportes.service.IReportesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService implements IReportesService {
    private final MonopatinFeignClient monopatinFeignClient;
    private final ViajeFeignClient viajeFeignClient;

    @Override
    public List<ViajeReporteResponseDTO> generarReporteViajesMonopatines(ViajeReporteRequestDTO viajeReporteRequestDTO) {
        return viajeFeignClient.obtenerReporteDeViajesPorMonopatin(viajeReporteRequestDTO);
    }

    @Override
    public String obtenerTotalFacturadoEnRangoDeMeses(TransaccionFiltroDTO transaccionFiltroDTO) {
        return "";
    }

    @Override
    public List<UsuarioReporteResponseDTO> obtenerTopUsuariosPorUso() {
        return List.of();
    }


}
