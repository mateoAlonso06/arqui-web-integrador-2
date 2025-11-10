package com.integrador.tpe.msvcviajes.service.impl;

import com.integrador.tpe.msvcviajes.clients.*;
import com.integrador.tpe.msvcviajes.dto.interservice.request.ViajeReporteRequestDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.utils.EstadoMonopatin;
import com.integrador.tpe.msvcviajes.dto.interservice.utils.UbicacionGPS;
import com.integrador.tpe.msvcviajes.dto.interservice.request.InformacionViaje;
import com.integrador.tpe.msvcviajes.dto.interservice.response.MonopatinResponseDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ParadaResponseDTO;
import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.entity.Pausa;
import com.integrador.tpe.msvcviajes.entity.Viaje;
import com.integrador.tpe.msvcviajes.exception.UsuarioNotFoundException;
import com.integrador.tpe.msvcviajes.exception.ViajeNotFoundException;
import com.integrador.tpe.msvcviajes.mapper.ViajeMapper;
import com.integrador.tpe.msvcviajes.repository.ViajeRepository;
import com.integrador.tpe.msvcviajes.service.IViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViajeService implements IViajeService {
    private final ViajeRepository viajeRepository;

    private final ViajeMapper viajeMapper;

    private final MonopatinFeignClient monopatinClient;
    private final UsuarioFeignClient usuarioClient;
    private final CuentaFeignClient cuentaClient;
    private final FacturacionFeignClient facturacionClient;
    private final ParadaFeignClient paradaClient;

    @Override
    @Transactional
    public ViajeResponseDTO iniciarViaje(ViajeRequestDTO viajeRequestDTO) {
        String idMonopatin = viajeRequestDTO.getIdMonopatin();
        String idParada = viajeRequestDTO.getIdParada();
        Long idUsuario = viajeRequestDTO.getIdUsuario();
        Long idCuenta = viajeRequestDTO.getIdCuenta();

        ParadaResponseDTO paradaInicio = paradaClient.getParadaById(idParada);
        MonopatinResponseDTO monopatin = monopatinClient.getMonopatinById(idMonopatin);

        if (!facturacionClient.activoServicio(idCuenta))
            throw new IllegalStateException("El servicio de facturación no está activo para la cuenta con id " + idCuenta + ".");

        if (facturacionClient.tieneDeudasPendientes(idCuenta))
            throw new IllegalStateException("La cuenta con id " + idCuenta + " tiene deudas pendientes.");

        if (!monopatin.getUbicacionGps().equals(paradaInicio.getUbicacionGps()) && paradaInicio.getId().equals(monopatin.getIdParada()))
            throw new IllegalStateException("EL monopatín no se encuentra en la ubicación de la parada.");

        if (!monopatin.getEstado().equals(EstadoMonopatin.LIBRE))
            throw new IllegalStateException("El monopatín con id " + idMonopatin + " no está disponible para iniciar un viaje.");

        // con JWT la informacion la sacamos de ahi
        if (!usuarioClient.existsUsuarioById(idUsuario))
            throw new UsuarioNotFoundException("El usuario con id " + idUsuario + " no existe.");

        if (!cuentaClient.isCuentaHabilitada(idCuenta))
            throw new IllegalStateException("La cuenta con id " + idCuenta + " no está habilitada.");

        if (!usuarioClient.estaAsociadoConCuenta(idUsuario, idCuenta))
            throw new IllegalStateException("El usuario con id " + idUsuario + " no está asociado con la cuenta con id " + idCuenta + ".");

        if (viajeRepository.existsViajeByIdUsuarioAndFechaFinIsNull(idUsuario))
            throw new IllegalStateException("El usuario con id " + idUsuario + " ya tiene un viaje activo.");

        monopatinClient.actualizarEstadoMonopatin(idMonopatin, "EN_USO");

        Viaje viaje = Viaje.builder()
                .idMonopatin(idMonopatin)
                .idUsuario(idUsuario)
                .idCuenta(idCuenta)
                .fechaInicio(LocalDateTime.now())
                .build();

        Viaje saved = viajeRepository.save(viaje);
        return viajeMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void finalizarViaje(Long idViaje, ViajeRequestDTO viajeRequestDTO) {
        String idMonopatin = viajeRequestDTO.getIdMonopatin();
        String idParada = viajeRequestDTO.getIdParada();
        Long idUsuario = viajeRequestDTO.getIdUsuario();
        Long idCuenta = viajeRequestDTO.getIdCuenta();

        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado."));

        ParadaResponseDTO paradaFin = paradaClient.getParadaById(idParada);

        monopatinClient.actualizarUbicacionMonopatin(idMonopatin, paradaFin.getUbicacionGps());

        MonopatinResponseDTO monopatin = monopatinClient.getMonopatinById(idMonopatin);

        System.out.println(monopatin.getUbicacionGps());
        System.out.println(paradaFin.getUbicacionGps());

        // verificar que a la parada que se llega no sea la misma del comienzo
        // verificar que kmRecorridos no sea null y sea mayor a 0

        if (viaje.getFechaFin() != null)
            throw new IllegalStateException("El viaje con id " + idViaje + " ya ha sido finalizado.");

        UbicacionGPS ubicacionMonopatin = monopatin.getUbicacionGps();
        UbicacionGPS ubicacionParadaFin = paradaFin.getUbicacionGps();

        if (!ubicacionMonopatin.equals(ubicacionParadaFin))
            throw new IllegalStateException("El monopatín no se encuentra en la ubicación de la parada de finalización.");

        viaje.setFechaFin(LocalDateTime.now());
        viaje.setKmRecorridos(viajeRequestDTO.getKmRecorridos());
        viajeRepository.save(viaje);

        List<Viaje> viajes = viajeRepository.findAllByIdUsuario(idUsuario);

        Double kmHechosPorElUsuario = viajes.stream()
                .mapToDouble(Viaje::getKmRecorridos)
                .sum();

        Long duracionViaje = Duration.between(viaje.getFechaInicio(), viaje.getFechaFin()).toMinutes();

        double tiempoDePausa = 0.0;
        for (Pausa p : viaje.getPausas())
            tiempoDePausa += Duration.between(p.getFechaInicio(), p.getFechaFin()).toMinutes();

        monopatinClient.actualizarRecorridoMonopatin(idMonopatin, viajeRequestDTO.getKmRecorridos());
        monopatinClient.actualizarEstadoMonopatin(idMonopatin, "LIBRE");

        InformacionViaje info = InformacionViaje.builder()
                .idViaje(idViaje)
                .idCuenta(idCuenta)
                .idMonopatin(idMonopatin)
                .fechaInicio(viaje.getFechaInicio())
                .tipoCuenta(cuentaClient.getTipoCuenta(idCuenta)) // "BASICA" o "PREMIUM"
                .kmHechosPorElUsuario(kmHechosPorElUsuario)
                .tiempoDePausa(tiempoDePausa)
                .duracionViaje(duracionViaje)
                .fechaFin(viaje.getFechaFin())
                .build();

        facturacionClient.generarFactura(idUsuario, info);
    }

    @Override
    @Transactional(readOnly = true)
    public ViajeResponseDTO getViajeById(Long idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado."));
        return viajeMapper.toResponseDTO(viaje);
    }

    @Override
    @Transactional
    public void deleteViajeById(Long idViaje) {
        if (!viajeRepository.existsById(idViaje)) {
            throw new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado.");
        }

        viajeRepository.deleteById(idViaje);
    }

    /***
     * Si la fecha es null, devuelve todos los viajes paginados.
     * Si la fecha no es null, devuelve los viajes que comenzaron en esa fecha paginados.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ViajeResponseDTO> getAllViajes(Pageable pageable, LocalDateTime fecha) {
        if (fecha != null) {
            return viajeRepository.findViajeByFechaInicio(fecha, pageable)
                    .map(viajeMapper::toResponseDTO);
        } else {
            return viajeRepository.findAll(pageable)
                    .map(viajeMapper::toResponseDTO);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViajeReporteResponseDTO> findAllViajesHechosPorAnioConCantidadViajesX(ViajeReporteRequestDTO viajeReporteRequestDTO) {
        int cantidadViajes = viajeReporteRequestDTO.cantidadViajes();
        int anio = viajeReporteRequestDTO.anio();
        return viajeRepository.findAllViajesHechosPorAnioConCantidadViajesX(cantidadViajes, anio);
    }
}
