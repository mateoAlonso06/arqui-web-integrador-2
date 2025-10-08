package org.integrador.repository.impl;

import org.integrador.dto.CarreraDTO;
import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Carrera;
import org.integrador.entity.Estudiante;
import org.integrador.factory.EntityManagerFactory;
import org.integrador.repository.ICarreraRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements ICarreraRepository {

    @Override
    public CarreraDTO addCarrera(Carrera carrera) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(carrera);
            em.getTransaction().commit();

            CarreraDTO carreraDTO = new CarreraDTO();
            carreraDTO.setId(carrera.getId());
            carreraDTO.setNombre(carrera.getNombre());
            carreraDTO.setDuracion(carrera.getDuracion());
            carreraDTO.setCantidadInscriptos(carrera.getEstudianteCarrera().size());
            return carreraDTO;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    public CarreraDTO getCarreraByName(String nombre) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        CarreraDTO carreraDTO = null;
        try {
            Carrera carrera = em.createQuery("SELECT c FROM Carrera c WHERE c.nombre = :nombre", Carrera.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();

            carreraDTO = new CarreraDTO();
            carreraDTO.setId(carrera.getId());
            carreraDTO.setNombre(carrera.getNombre());
            carreraDTO.setDuracion(carrera.getDuracion());
            carreraDTO.setCantidadInscriptos(carrera.getEstudianteCarrera().size());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
        return carreraDTO;
    }

    @Override
    public List<CarreraDTO> getAllCarrerasConInscriptos() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        List<CarreraDTO> carrerasDTO = new ArrayList<>();
        try {
            List<Carrera> carreras = em.createQuery("""
                        SELECT c
                        FROM EstudianteCarrera ec
                        JOIN ec.carrera c
                        GROUP BY c
                        ORDER BY COUNT(ec) DESC
                    """, Carrera.class).getResultList();

            for (Carrera carrera : carreras) {
                CarreraDTO dto = new CarreraDTO();
                dto.setId(carrera.getId());
                dto.setNombre(carrera.getNombre());
                dto.setDuracion(carrera.getDuracion());
                dto.setCantidadInscriptos(carrera.getEstudianteCarrera().size());
                carrerasDTO.add(dto);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return carrerasDTO;
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraId(Integer carreraId, String ciudad) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        List<EstudianteDTO> estudiantesDTO = new ArrayList<>();
        try {
            List<Estudiante> estudiantes = em.createQuery("""
                                SELECT e
                                FROM EstudianteCarrera ec
                                JOIN ec.estudiante e
                                WHERE ec.carrera.id = :carreraId AND ec.estudiante.ciudad = :ciudad
                            """, Estudiante.class)
                    .setParameter("carreraId", carreraId)
                    .setParameter("ciudad", ciudad)
                    .getResultList();

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
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return estudiantesDTO;
    }
}
