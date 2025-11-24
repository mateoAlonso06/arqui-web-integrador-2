package com.integrador.tpe.msvcviajes.repository;

import com.integrador.tpe.msvcviajes.dto.interservice.request.TipoCuenta;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteConsumoPersonalServicio;
import com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteUsoMonopatines;
import com.integrador.tpe.msvcviajes.dto.interservice.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeReporteResponseDTO;
import com.integrador.tpe.msvcviajes.dto.response.ViajeResponseDTO;
import com.integrador.tpe.msvcviajes.entity.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    boolean existsViajeByIdUsuarioAndFechaFinIsNull(Long idUsuario);

    Page<Viaje> findViajeByFechaInicio(LocalDateTime fecha, Pageable pageable);

    List<Viaje> findAllByIdUsuario(Long idUsuario);

    @Query("""
            SELECT new com.integrador.tpe.msvcviajes.dto.response.ViajeReporteResponseDTO(
                v.idMonopatin,
                COUNT(v),
                SUM(v.kmRecorridos)
            )
            FROM Viaje v 
            WHERE YEAR(v.fechaInicio) = :anio
            GROUP BY v.idMonopatin
            HAVING COUNT(v) >= :cantidadViajes  
            """)
    List<ViajeReporteResponseDTO> findAllViajesHechosPorAnioConCantidadViajesX(@Param("cantidadViajes") Integer cantidadViajes, @Param("anio") Integer anio);

    @Query("""
            SELECT new com.integrador.tpe.msvcviajes.dto.interservice.response.UsuarioResponseDTO(
                v.idUsuario,
                COUNT(v.idUsuario)
            )
            FROM Viaje v
            WHERE v.tipoCuenta = :tipoCuenta
                AND v.fechaInicio >= :fechaInicio
                AND v.fechaInicio <= :fechaFin
            GROUP BY v.idUsuario
            ORDER BY COUNT(v) DESC
            """)
    List<UsuarioResponseDTO> findAllUsuariosTop(@Param("tipoCuenta") TipoCuenta tipoCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    @Query("""
                SELECT new com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteUsoMonopatines(
                    v.idMonopatin,
                    COUNT(v),
                    SUM(v.kmRecorridos),
                    SUM(v.tiempoPausa)
                )
                FROM Viaje v
                GROUP BY v.idMonopatin
            """)
    List<ReporteUsoMonopatines> generarReporteUsoMonopatinesConPausa();


    @Query("""
                    SELECT new com.integrador.tpe.msvcviajes.dto.interservice.response.ReporteUsoMonopatines(
                        v.idMonopatin,
                        COUNT(v),
                        SUM(v.kmRecorridos)
                    )
                    FROM Viaje v
                    GROUP BY v.idMonopatin
            """)
    List<ReporteUsoMonopatines> generarReporteUsoMonopatinesSinPausa();

    @Query("""
                SELECT v
                FROM Viaje v
                WHERE v.idUsuario = :idUsuario
                AND v.fechaInicio BETWEEN :fechaInicio AND :fechaFin
            """)
    List<Viaje> generarReporteConsumoPersonalServicio(Long idUsuario, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
