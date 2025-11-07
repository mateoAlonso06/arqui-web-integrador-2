package com.integrador.tpe.msvfacturacion.service.impl;

import com.integrador.tpe.msvfacturacion.dto.response.CuentaCorrienteResponseDTO;
import com.integrador.tpe.msvfacturacion.entity.CuentaCorriente;
import com.integrador.tpe.msvfacturacion.entity.TipoTransaccion;
import com.integrador.tpe.msvfacturacion.entity.Transaccion;
import com.integrador.tpe.msvfacturacion.repository.CuentaCorrienteRepository;
import com.integrador.tpe.msvfacturacion.service.IFacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class FacturacionService implements IFacturacionService {
    private final CuentaCorrienteRepository cuentaCorrienteRepository;

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

        // persisto en ambos lados de la relacion
        cuentaCorriente.getTransacciones().add(transaccion);
        CuentaCorriente cuentaCorriento = cuentaCorrienteRepository.save(cuentaCorriente);
    }

    @Transactional
    public CuentaCorrienteResponseDTO debitarViaje(Long cuentaId, Long idViaje) {
        CuentaCorriente cuentaCorriente = cuentaCorrienteRepository.findByCuentaId(cuentaId)
                .orElseThrow(() -> new IllegalStateException("La cuenta corriente con id " + cuentaId + " no existe."));

        // validacion de que el viaje exista

        // tengo que obtener cuanto cobrar el usuario en base a la tarifa y el tiempo de uso del monopatin

        // Hay que verificar si el usuario es premium, si es premium hay que debitarle (ver logica de negocio)

        // que debo hacer en el caso de que el monto del viaje sea mayor al saldo disponible?
        if (cuentaCorriente.getSaldoActual().compareTo(monto) < 0)
            throw new IllegalStateException("Saldo insuficiente en la cuenta corriente con id " + cuentaId + ".");

        cuentaCorriente.setSaldoActual(cuentaCorriente.getSaldoActual().subtract(monto));
    }
}
