package com.integrador.springboot.repository;

import com.integrador.springboot.dto.responseDTO.Reporte;
import com.integrador.springboot.entity.Carrera;
import com.integrador.springboot.entity.Estudiante;
import com.integrador.springboot.entity.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Integer> {
    Optional<EstudianteCarrera> findByEstudianteAndCarrera(Estudiante estudiante, Carrera carrera);

    boolean existsEstudianteCarreraByCarreraAndEstudiante(Carrera carrera, Estudiante estudiante);

    @Query("SELECT new com.integrador.springboot.dto.responseDTO.Reporte(" +
            "c.nombre, " +
            "COUNT(ec.id), " +
            "SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END), " +
            "ec.anioInscripcion) " +
            "FROM EstudianteCarrera ec JOIN ec.carrera c " +
            "GROUP BY c.nombre, ec.anioInscripcion " +
            "ORDER BY c.nombre ASC, ec.anioInscripcion ASC")
    List<Reporte> obtenerReporteEstudiantesPorCarrera();
}
