package org.integrador.repository.impl;

import org.integrador.dto.CarreraDTO;
import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Carrera;
import org.integrador.entity.Estudiante;
import org.integrador.factory.JPAUtil;
import org.integrador.repository.ICarreraRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements ICarreraRepository {

    private final EntityManager em = JPAUtil.getEntityManager();

    @Override
    public CarreraDTO addCarrera(Carrera carreraDTO) {
        try {
            em.getTransaction().begin();
            em.persist(carreraDTO);
            em.getTransaction().commit();

            CarreraDTO cDTO = new CarreraDTO();
            cDTO.setId(carreraDTO.getId());
            cDTO.setNombre(carreraDTO.getNombre());
            cDTO.setDuracion(carreraDTO.getDuracion());

            return cDTO;

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

    @Override
    public List<CarreraDTO> getAllCarrerasConInscriptos() {
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
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return carrerasDTO;
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraId(Integer carreraId, String ciudad) {
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
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return estudiantesDTO;
    }
}
