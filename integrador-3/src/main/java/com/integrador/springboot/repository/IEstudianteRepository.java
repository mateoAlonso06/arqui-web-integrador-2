package com.integrador.springboot.repository;

import com.integrador.springboot.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEstudianteRepository extends JpaRepository<Estudiante, String> {
    Optional<Estudiante> findByLibretaUniversitaria(String libretaUniversitaria);

    List<Estudiante> findByGenero(String genero);
}
