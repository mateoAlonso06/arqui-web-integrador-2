package com.integrador.tpe.msvcviajes.service.impl;

import com.integrador.tpe.msvcviajes.clients.CuentaFeignClient;
import com.integrador.tpe.msvcviajes.clients.MonopatinFeignCLient;
import com.integrador.tpe.msvcviajes.clients.UsuarioFeignClient;
import com.integrador.tpe.msvcviajes.dto.request.ViajeRequestDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.exception.UsuarioNotFoundException;
import com.integrador.tpe.msvcviajes.mapper.ViajeMapper;
import com.integrador.tpe.msvcviajes.repository.ViajeRepository;
import com.integrador.tpe.msvcviajes.service.IViajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ViajeService implements IViajeService {
    private final ViajeRepository viajeRepository;

    private final ViajeMapper viajeMapper;

    private final MonopatinFeignCLient monopatinClient;
    private final UsuarioFeignClient usuarioClient;
    private final CuentaFeignClient cuentaClient;
    private final FacturacionFeignClient facturacionClient;

    @Override
    public ViajeResponseDTO iniciarViaje(ViajeRequestDTO viajeRequestDTO) {
        Long idMonopatin = viajeRequestDTO.idMonopatin();
        Long idUsuario = viajeRequestDTO.idUsuario();
        Long idCuenta = viajeRequestDTO.idCuenta();

        // TODO: Cuando se lancen excepciones a nivel de microservicio externo, acá llegara un codigo distinto a 2xx y se manejara en un handler
        if (!monopatinClient.verificarDisponibilidadMonopatin(idMonopatin)) // comprueba tambien que el monopatin exista
            throw new IllegalStateException("El monopatín no está disponible para iniciar un viaje.");

        if (!usuarioClient.existUsuarioById(idUsuario))
            throw new UsuarioNotFoundException("El usuario con id " + idUsuario + " no existe.");

        if (!cuentaClient.isCuentaHabilitada(idCuenta)) // Valida que la cuenta no este anulada
            throw new IllegalStateException("La cuenta con id " + idCuenta + " no está habilitada.");

        if (!usuarioClient.estaAsociadoConCuenta(idUsuario, idCuenta))
            throw new IllegalStateException("El usuario con id " + idUsuario + " no está asociado con la cuenta con id " + idCuenta + ".");

        if (!cuentaClient.isCuentaHabilitada(idCuenta)) // Valida que la cuenta no este anulada
            throw new IllegalStateException("La cuenta con id " + idCuenta + " no está habilitada.");

        // if tiene saldo disponible

        // if usuario no tiene viajes activos . verifica que un viaje asociado a un usuario aun no tenga fecha de finalizacion
    }

    @Override
    public ViajeResponseDTO getViajeById(Long idViaje) {
        return null;
    }

    @Override
    public void deleteViajeById(Long idViaje) {

    }

    @Override
    public Page<ViajeResponseDTO> getAllViajes(Pageable pageable, LocalDateTime fechaInicio) {
        return null;
    }
}
