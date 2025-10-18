package com.integrador.springboot.repository;

import com.integrador.springboot.entity.Carrera;
import com.integrador.springboot.entity.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICarreraRepository extends JpaRepository<Carrera, Integer> {
    @Query("SELECT c FROM Carrera c JOIN c.estudianteCarrera ec GROUP BY c ORDER BY COUNT(ec) DESC")
    Page<Carrera> getCarrerasConInscriptos(Pageable pageable);

    @Query("SELECT e FROM Carrera c JOIN c.estudianteCarrera ec JOIN ec.estudiante e WHERE c = :carrera AND e.ciudad = :ciudad")
    List<Estudiante> getEstudiantesByCarrera(Carrera carrera, String ciudad);

    Integer id(Integer id);
}
