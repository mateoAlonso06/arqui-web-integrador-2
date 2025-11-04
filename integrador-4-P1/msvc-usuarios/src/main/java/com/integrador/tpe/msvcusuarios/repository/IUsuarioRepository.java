package com.integrador.tpe.msvcusuarios.repository;

import com.integrador.tpe.msvcusuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
}
