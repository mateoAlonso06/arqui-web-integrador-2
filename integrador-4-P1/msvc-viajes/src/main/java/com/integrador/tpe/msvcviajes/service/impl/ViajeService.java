package com.integrador.tpe.msvcviajes.service.impl;

import com.integrador.tpe.msvcviajes.clients.*;
import com.integrador.tpe.msvcviajes.dto.interservice.InformacionViaje;
import com.integrador.tpe.msvcviajes.dto.interservice.MonopatinResponseDTO;
import com.integrador.tpe.msvcviajes.dto.interservice.ParadaResponseDTO;
import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
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
    public ViajeResponseDTO iniciarViaje(ViajeRequestDTO viajeRequestDTO) {
        Long idMonopatin = viajeRequestDTO.getIdMonopatin();
        Long idUsuario = viajeRequestDTO.getIdUsuario();
        Long idCuenta = viajeRequestDTO.getIdCuenta();

        // Cuando se lancen excepciones a nivel de microservicio externo, acá llegara un codigo distinto a 2xx y se manejara en un handler
        if (!monopatinClient.verificarDisponibilidadMonopatin(idMonopatin)) // monopatin libre y funcionando
            throw new IllegalStateException("El monopatín no está disponible para iniciar un viaje.");

        if (!usuarioClient.existUsuarioById(idUsuario)) // TODO: cuando se implemente JWT quizas haya que borrar esta verifacion
            throw new UsuarioNotFoundException("El usuario con id " + idUsuario + " no existe.");

        if (!cuentaClient.isCuentaHabilitada(idCuenta))
            throw new IllegalStateException("La cuenta con id " + idCuenta + " no está habilitada.");

        if (!usuarioClient.estaAsociadoConCuenta(idUsuario, idCuenta))
            throw new IllegalStateException("El usuario con id " + idUsuario + " no está asociado con la cuenta con id " + idCuenta + ".");

        if (viajeRepository.existsViajeByIdUsuarioAndFechaFinIsNull(idUsuario))
            throw new IllegalStateException("El usuario con id " + idUsuario + " ya tiene un viaje activo.");

        // if: que hacer con el saldo de la cuenta para iniciar un viaje

        Viaje viaje = Viaje.builder()
                .idMonopatin(idMonopatin)
                .idUsuario(idUsuario)
                .idCuenta(idCuenta)
                .fechaInicio(LocalDateTime.now())
                .build();

        Viaje saved = viajeRepository.save(viaje);
        return viajeMapper.toResponseDTO(saved);
    }

    public void finalizarViaje(Long idViaje, Long idParada, ViajeRequestDTO viajeRequestDTO) {
        Long idMonopatin = viajeRequestDTO.getIdMonopatin();
        Long idUsuario = viajeRequestDTO.getIdUsuario();
        Long idCuenta = viajeRequestDTO.getIdCuenta();

        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado."));

        if (viaje.getFechaFin() != null)
            throw new IllegalStateException("El viaje con id " + idViaje + " ya ha sido finalizado.");

        ParadaResponseDTO paradaFin = paradaClient.getParadaById(idParada);
        MonopatinResponseDTO monopatin = monopatinClient.getMonopatinById(idMonopatin);

        if (monopatin.getUbicacionGPS().equals(paradaFin.getUbicacionGPS()) && paradaFin.getId().equals(monopatin.getIdParada())) {
            viaje.setFechaFin(LocalDateTime.now());
            viaje.setKmRecorridos(viajeRequestDTO.getKmRecorridos());
            viajeRepository.save(viaje); // actualizo el viaje con la fecha de fin
        } else {
            throw new IllegalStateException("El monopatín no se encuentra en la ubicación de la parada de finalización.");
        }

        List<Viaje> viajes = viajeRepository.findAllByIdUsuario(idUsuario);

        Double kmHechosPorElUsuario = viajes.stream()
                .mapToDouble(Viaje::getKmRecorridos)
                .sum();

        monopatinClient.actualizarRecorridoMonopatin(idMonopatin, viajeRequestDTO.getKmRecorridos());

        InformacionViaje info = InformacionViaje.builder()
                .idViaje(idViaje)
                .idCuenta(idCuenta)
                .idUsuario(idUsuario)
                .idMonopatin(idMonopatin)
                .fechaInicio(viaje.getFechaInicio())
                .tipoCuenta(cuentaClient.getTipoCuenta(idCuenta)) // "BASICA" o "PREMIUM"
                .kmHechosPorElUsuario(kmHechosPorElUsuario)
                .fechaFin(viaje.getFechaFin())
                .build();
    }

    @Override
    public ViajeResponseDTO getViajeById(Long idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado."));
        return viajeMapper.toResponseDTO(viaje);
    }

    @Override
    public void deleteViajeById(Long idViaje) {
        // Van a quedar registros de viajes en facturacion probablemente apuntando hacia viajes que no existen
        if (!viajeRepository.existsById(idViaje)) {
            throw new ViajeNotFoundException("Viaje con id " + idViaje + " no encontrado.");
        }

//      if : existe viaje debo hacer algo con la informacion en las facturaciones

        viajeRepository.deleteById(idViaje);
    }

    @Override
    public Page<ViajeResponseDTO> getAllViajes(Pageable pageable, LocalDateTime fecha) {
        if (fecha != null) {
            return viajeRepository.findViajeByFechaInicio(fecha, pageable)
                    .map(viajeMapper::toResponseDTO);
        } else {
            return viajeRepository.findAll(pageable)
                    .map(viajeMapper::toResponseDTO);
        }
    }
}
