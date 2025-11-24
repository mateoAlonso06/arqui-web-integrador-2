package com.integrador.tpe.msvcfacturacion.service.impl;

import com.integrador.tpe.msvcfacturacion.clients.TarifaFeignClient;
import com.integrador.tpe.msvcfacturacion.dto.interservice.TarifaDTO;
import com.integrador.tpe.msvcfacturacion.dto.request.CargaSaldoDTO;
import com.integrador.tpe.msvcfacturacion.dto.request.InformacionViaje;
import com.integrador.tpe.msvcfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvcfacturacion.entity.CuentaCorriente;
import com.integrador.tpe.msvcfacturacion.entity.TipoTransaccion;
import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import com.integrador.tpe.msvcfacturacion.mapper.CuentaCorrienteMapper;
import com.integrador.tpe.msvcfacturacion.repository.CuentaCorrienteRepository;
import com.integrador.tpe.msvcfacturacion.repository.TransaccionRepository;
import com.integrador.tpe.msvcfacturacion.service.IFacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FacturacionService implements IFacturacionService {
    private final CuentaCorrienteRepository cuentaCorrienteRepository;
    private final TransaccionRepository transaccionRepository;
    private final CuentaCorrienteMapper cuentaCorrienteMapper;
    private final TarifaFeignClient tarifaClient;

    @Override
    @Transactional
    public CuentaCorrienteResponseDTO cargarSaldo(Long cuentaId, CargaSaldoDTO cargaSaldoDTO) { // Las validaciones de los parametros las tengo a nivel de DTO
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByIdCuenta(cuentaId)
                .orElseGet(() -> {
                    CuentaCorriente nuevaCuenta = new CuentaCorriente();
                    nuevaCuenta.setIdCuenta(cuentaId);
                    nuevaCuenta.setSaldoActual(new BigDecimal("0.0"));
                    return nuevaCuenta;
                });

        BigDecimal monto = cargaSaldoDTO.monto();

        cuentaCorriente.setSaldoActual(cuentaCorriente.getSaldoActual().add(monto));

        Transaccion transaccion = Transaccion.builder()
                .idCuenta(cuentaId)
                .monto(monto)
                .idViaje(null)
                .tipo(TipoTransaccion.CARGA_SALDO)
                .cuentaCorriente(cuentaCorriente) // seteo fk
                .build();

        cuentaCorriente.getTransacciones().add(transaccion);
        CuentaCorriente cuentaCorriento = cuentaCorrienteRepository.save(cuentaCorriente);
        return cuentaCorrienteMapper.toResponseDTO(cuentaCorriento);
    }

    @Override
    @Transactional
    public void debitarViaje(Long idUsuario, InformacionViaje informacionViaje) {
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByIdCuenta(informacionViaje.getIdCuenta())
                .orElseThrow(() -> new IllegalStateException("La cuenta corriente con id " + informacionViaje.getIdCuenta() + " no existe."));

        if (transaccionRepository.existsByIdViajeAndCuentaCorriente_IdCuenta((informacionViaje.getIdViaje()), informacionViaje.getIdCuenta()))
            throw new IllegalStateException("El viaje con id " + informacionViaje.getIdViaje() + " ya fue debitado en la cuenta corriente con id " + informacionViaje.getIdCuenta() + ".");

        boolean usuarioPremium = informacionViaje.getTipoCuenta().equalsIgnoreCase("PREMIUM");
        boolean tarifaExtraPausa = informacionViaje.getTiempoDePausa() > 15.0; // minutos

        TarifaDTO montoTarifas = tarifaClient.getTarifas();
        BigDecimal montoACobrar = BigDecimal.ZERO;
        BigDecimal duracionSinPausa = BigDecimal.valueOf(informacionViaje.getDuracionViaje())
                .subtract(BigDecimal.valueOf(informacionViaje.getTiempoDePausa()));

        if (tarifaExtraPausa) {
            montoACobrar = montoACobrar.add(
                    montoTarifas.getTarifaPausa().multiply(BigDecimal.valueOf(informacionViaje.getTiempoDePausa()))
            );
        }
        if (!usuarioPremium) {
            montoACobrar = montoACobrar.add(
                    montoTarifas.getTarifaBase().multiply(duracionSinPausa)
            );
        } else {
            if (informacionViaje.getKmHechosPorElUsuario() > 100) {
                montoACobrar = montoACobrar.add(
                        montoTarifas.getTarifaBase().multiply(duracionSinPausa)
                                .divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_UP)
                );
            }
        }

        cuentaCorriente.setSaldoActual(cuentaCorriente.getSaldoActual().subtract(montoACobrar));

        Transaccion transaccion = Transaccion.builder()
                .idCuenta(informacionViaje.getIdCuenta())
                .monto(montoACobrar)
                .idViaje(informacionViaje.getIdViaje())
                .tipo(TipoTransaccion.PAGO_VIAJE)
                .cuentaCorriente(cuentaCorriente) // seteo fk
                .build();

        cuentaCorriente.getTransacciones().add(transaccion);
        cuentaCorrienteRepository.save(cuentaCorriente);
    }

    @Override
    public boolean tieneDeudasPendientes(Long idCuenta) {
        return cuentaCorrienteRepository.existsByIdCuentaAndSaldoActualLessThanEqual(idCuenta, BigDecimal.ZERO);
    }

    @Override
    public boolean activoServicio(Long idCuenta) {
        return cuentaCorrienteRepository.existsById(idCuenta);
    }
}
