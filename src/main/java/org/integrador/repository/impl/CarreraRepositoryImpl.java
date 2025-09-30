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


    SELECT c.nombre, COUNT(*)
    FROM estudianteCarrera ec
    JOIN carrera c ON ec.id_carrera = c.id
    GROUP BY c.nombre
    ORDER BY 2 DESC;
    CONSULTA PARA LA FUNCION DE ABAJO

    @Override
    public List<CarreraDTO> getAllCarrerasConInscriptos() {
        List<CarreraDTO> carrerasDTO = new ArrayList<>();
        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT ec.carrera.nombre, count(*) FROM EstudianteCarrera ec GROUP BY " +
                            "ec.carrera" + Carrera.class)
                    .getResultList();
            for (Estudiante estudiante : estudiantes) {
                Carrera carrera = estudiante.getCarrera();
                if (carrera != null) {
                    CarreraDTO carreraDTO = new CarreraDTO();
                    carreraDTO.setId(carrera.getId());
                    carreraDTO.setNombre(carrera.getNombre());
                    carreraDTO.setDuracion(carrera.getDuracion());

                    if (!carrerasDTO.contains(carreraDTO)) {
                        carrerasDTO.add(carreraDTO);
                    }
                }
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return ;
    }

    @Override
    public List<EstudianteDTO> getEstudiantesByCarreraId(Integer carreraId) {
        return List.of();
    }
}
