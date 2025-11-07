package com.integrador.tpe.msvfacturacion.repository;

import com.integrador.tpe.msvfacturacion.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    boolean existsByIdViaje(Long idViaje);

    boolean existsByIdViajeAndCuentaCorriente_IdCuenta(Long idViaje, Long cuentaCorrienteIdCuenta);
}
