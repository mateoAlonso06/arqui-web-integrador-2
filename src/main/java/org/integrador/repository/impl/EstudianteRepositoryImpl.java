package org.integrador.repository.impl;

import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Estudiante;
import org.integrador.factory.JPAUtil;
import org.integrador.repository.IEstudianteRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class EstudianteRepositoryImpl implements IEstudianteRepository {
    private final EntityManager em = JPAUtil.getEntityManager();

    @Override
    public EstudianteDTO addEstudiante(EstudianteDTO estudiante) {
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<EstudianteDTO> getAllEstudiantes(int page, int size) {
        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e ORDER BY e.apellido", Estudiante.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

            List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

            for (Estudiante estudiante : estudiantes) {
                EstudianteDTO dto = new EstudianteDTO();
                dto.setDni(estudiante.getDni());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad(estudiante.getCiudad());
                dto.setLibretaUniversitaria(estudiante.getLibretaUniversitaria());

                estudiantesDTO.add(dto);
            }
            return estudiantesDTO;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public EstudianteDTO getEstudianteByLibretaUniversitaria(String libretaUniversitaria) {
        try {
            Estudiante estudiante = em.createQuery("SELECT e FROM Estudiante e WHERE e.libretaUniversitaria = :libreta", Estudiante.class)
                    .setParameter("libreta", libretaUniversitaria)
                    .getSingleResult();

            if (estudiante != null) {
                EstudianteDTO dto = new EstudianteDTO();
                dto.setDni(estudiante.getDni());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad(estudiante.getCiudad());
                dto.setLibretaUniversitaria(estudiante.getLibretaUniversitaria());
                return dto;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByGenero(String genero) {
        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();

            List<EstudianteDTO> estudiantesDTO = new ArrayList<>();

            for (Estudiante estudiante : estudiantes) {
                EstudianteDTO dto = new EstudianteDTO();
                dto.setDni(estudiante.getDni());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad(estudiante.getCiudad());
                dto.setLibretaUniversitaria(estudiante.getLibretaUniversitaria());

                estudiantesDTO.add(dto);
            }
            return estudiantesDTO;
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
    }
}
