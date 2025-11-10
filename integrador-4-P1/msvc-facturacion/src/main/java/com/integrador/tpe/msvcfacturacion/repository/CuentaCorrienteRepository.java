package com.integrador.tpe.msvcfacturacion.repository;

import com.integrador.tpe.msvcfacturacion.entity.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {

    Optional<CuentaCorriente> findByIdCuenta(Long idCuenta);

    boolean existsByIdCuentaAndSaldoActualLessThanEqual(Long idCuenta, BigDecimal zero);
}
