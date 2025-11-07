package com.integrador.tpe.msvfacturacion.repository;

import com.integrador.tpe.msvfacturacion.entity.CuentaCorriente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaCorrienteRepository extends JpaRepository<CuentaCorriente, Long> {

    Optional<CuentaCorriente> findByCuentaId(Long cuentaId);
}
