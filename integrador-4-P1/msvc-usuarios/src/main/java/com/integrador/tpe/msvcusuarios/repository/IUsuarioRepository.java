package com.integrador.tpe.msvcusuarios.repository;

import com.integrador.tpe.msvcusuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    boolean existsByIdAndCuentas_Id(Long idUsuario, Long idCuenta);

    Optional<Usuario> findByEmail(String email);
}
