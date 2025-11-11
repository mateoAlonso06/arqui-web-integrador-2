package com.integrador.tpe.msvcfacturacion.repository;

import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    boolean existsByIdViaje(Long idViaje);

    boolean existsByIdViajeAndCuentaCorriente_IdCuenta(Long idViaje, Long cuentaCorrienteIdCuenta);

    List<Transaccion> findAll(Specification<Transaccion> spec);
}
