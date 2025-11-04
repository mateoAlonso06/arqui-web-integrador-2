package com.integrador.tpe.msvcusuarios.repository;

import com.integrador.tpe.msvcusuarios.entity.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
    Page<Cuenta> findCuentaByEstadoCuenta(Boolean estadoCuenta, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(cu) > 0 THEN true ELSE false END " +
            "FROM Cuenta c JOIN c.usuarios cu " +
            "WHERE c.id = :idCuenta AND cu.id = :idUsuario")
    boolean existeAsociacionCuentaUsuario(Long idCuenta, Long idUsuario);

    boolean existsCuentaByIdCuentaMercadoPago(Long idCuentaMercadoPago);
}
