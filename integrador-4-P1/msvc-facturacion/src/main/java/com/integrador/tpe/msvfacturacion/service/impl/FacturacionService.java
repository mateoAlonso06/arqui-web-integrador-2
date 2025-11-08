package com.integrador.tpe.msvfacturacion.service.impl;

import com.integrador.tpe.msvfacturacion.client.TarifaFeignClient;
import com.integrador.tpe.msvfacturacion.dto.interservice.TarifaDTO;
import com.integrador.tpe.msvfacturacion.dto.request.InformacionViaje;
import com.integrador.tpe.msvfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvfacturacion.entity.CuentaCorriente;
import com.integrador.tpe.msvfacturacion.entity.TipoTransaccion;
import com.integrador.tpe.msvfacturacion.entity.Transaccion;
import com.integrador.tpe.msvfacturacion.mapper.CuentaCorrienteMapper;
import com.integrador.tpe.msvfacturacion.repository.CuentaCorrienteRepository;
import com.integrador.tpe.msvfacturacion.repository.TransaccionRepository;
import com.integrador.tpe.msvfacturacion.service.IFacturacionService;
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

    @Transactional
    public CuentaCorrienteResponseDTO cargarSaldo(Long cuentaId, BigDecimal monto) { // Las validaciones de los parametros las tengo a nivel de DTO
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCuentaId(cuentaId)
                .orElseGet(() -> {
                    CuentaCorriente nuevaCuenta = new CuentaCorriente();
                    nuevaCuenta.setIdCuenta(cuentaId);
                    nuevaCuenta.setSaldoActual(new BigDecimal(0.0));
                    return nuevaCuenta;
                });
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

    @Transactional
    public void debitarViaje(InformacionViaje informacionViaje) {
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCuentaId(informacionViaje.getIdCuenta())
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
            montoACobrar = montoTarifas.getTarifaBase().multiply(duracionSinPausa);
        } else {
            if (informacionViaje.getKmHechosPorElUsuario() > 100) {
                montoACobrar = montoTarifas.getTarifaBase().multiply(duracionSinPausa)
                        .divide(BigDecimal.valueOf(2), BigDecimal.ROUND_HALF_UP);
            }
        }

        // TODO: consultar por el saldo insuficiente o negativo

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
}
