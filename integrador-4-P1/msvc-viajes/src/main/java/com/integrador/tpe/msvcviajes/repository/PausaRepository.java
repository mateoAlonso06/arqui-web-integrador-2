package com.integrador.tpe.msvcviajes.repository;

import com.integrador.tpe.msvcviajes.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PausaRepository extends JpaRepository<Pausa, Long> {
    Optional<Pausa> findByIdAndViaje_Id(Long id, Long viajeId);
}
