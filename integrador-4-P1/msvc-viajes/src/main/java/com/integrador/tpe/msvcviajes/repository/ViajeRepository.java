package com.integrador.tpe.msvcviajes.repository;

import com.integrador.tpe.msvcviajes.dto.interservice.response.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcviajes.entity.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    boolean existsViajeByIdUsuarioAndFechaFinIsNull(Long idUsuario);

    Page<Viaje> findViajeByFechaInicio(LocalDateTime fecha, Pageable pageable);

    List<Viaje> findAllByIdUsuario(Long idUsuario);

    @Query("""
            SELECT new com.integrador.tpe.msvcviajes.dto.interservice.response.ViajeReporteResponseDTO(
                v.idMonopatin,
                COUNT(v) AS cantidadViajes,
                SUM(v.kmRecorridos)
            )
            FROM Viaje v 
            WHERE EXTRACT(YEAR FROM v.fechaInicio) = :anio
            GROUP BY v.idMonopatin
            HAVING COUNT(v) =:cantidadViajes  
            """)
    List<ViajeReporteResponseDTO> findAllViajesHechosPorAnioConCantidadViajesX(int cantidadViajes, int anio);
}
