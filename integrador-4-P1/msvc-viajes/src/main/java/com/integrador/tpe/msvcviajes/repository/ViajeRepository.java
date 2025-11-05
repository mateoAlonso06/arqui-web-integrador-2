package com.integrador.tpe.msvcviajes.repository;

import com.integrador.tpe.msvcviajes.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
