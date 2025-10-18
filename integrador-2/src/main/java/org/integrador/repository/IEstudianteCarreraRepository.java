package org.integrador.repository;


import org.integrador.dto.EstudianteCarreraDTO;
import org.integrador.entity.EstudianteCarrera;

public interface IEstudianteCarreraRepository {
    EstudianteCarreraDTO addEstudianteCarrera(EstudianteCarrera estudianteCarrera);
}