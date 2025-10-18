package com.integrador.springboot.repository;

import com.integrador.springboot.entity.Carrera;
import com.integrador.springboot.entity.Estudiante;
import com.integrador.springboot.entity.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Integer> {
    Optional<EstudianteCarrera> findByEstudianteAndCarrera(Estudiante estudiante, Carrera carrera);

    boolean existsEstudianteCarreraByCarreraAndEstudiante(Carrera carrera, Estudiante estudiante);
}
