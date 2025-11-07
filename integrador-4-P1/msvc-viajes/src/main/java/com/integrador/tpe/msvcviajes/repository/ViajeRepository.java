package com.integrador.tpe.msvcviajes.repository;

import com.integrador.tpe.msvcviajes.entity.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    boolean existsViajeByIdUsuarioAndFechaFinIsNull(Long idUsuario);

    Page<Viaje> findViajeByFechaInicio(LocalDateTime fecha, Pageable pageable);

    List<Viaje> findAllByIdUsuario(Long idUsuario);
}
