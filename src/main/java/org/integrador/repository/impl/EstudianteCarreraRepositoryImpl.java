package org.integrador.repository.impl;


import org.integrador.dto.EstudianteCarreraDTO;
import org.integrador.entity.EstudianteCarrera;
import org.integrador.factory.JPAUtil;
import org.integrador.repository.IEstudianteCarreraRepository;

import javax.persistence.EntityManager;

public class EstudianteCarreraRepositoryImpl implements IEstudianteCarreraRepository {
    private final EntityManager em = JPAUtil.getEntityManager();

    public EstudianteCarreraDTO addEstudianteCarrera(EstudianteCarrera estudianteCarrera) {
        try {
            em.getTransaction().begin();
            em.persist(estudianteCarrera);
            em.getTransaction().commit();

            EstudianteCarreraDTO estudianteCarreraDTO = new EstudianteCarreraDTO();
            estudianteCarreraDTO.setIdCarrera(estudianteCarrera.getId());
            estudianteCarreraDTO.setIdEstudiante(estudianteCarrera.getEstudiante().getDni());
            estudianteCarreraDTO.setAntiguedad(estudianteCarrera.getAntiguedad());
            estudianteCarreraDTO.setAnioInscripcion(estudianteCarrera.getAnioInscripcion());
            estudianteCarreraDTO.setGraduacion(estudianteCarrera.getGraduacion());

            return estudianteCarreraDTO;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
