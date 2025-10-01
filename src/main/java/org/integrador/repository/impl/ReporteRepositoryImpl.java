package org.integrador.repository.impl;

import lombok.RequiredArgsConstructor;
import org.integrador.dto.Reporte;
import org.integrador.factory.JPAUtil;
import org.integrador.repository.IReporteRepository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ReporteRepositoryImpl implements IReporteRepository {
    private EntityManager em = JPAUtil.getEntityManager();

    @Override
    public List<Reporte> getReporte() {
        try {
            return em.createQuery(
                    "SELECT new org.integrador.dto.Reporte(" +
                            "c.nombre, " +
                            "COUNT(ec.id), " +
                            "SUM(CASE WHEN ec.graduacion IS NOT NULL THEN 1 ELSE 0 END), " +
                            "ec.anioInscripcion" +
                            ") " +
                            "FROM EstudianteCarrera ec JOIN ec.carrera c " +
                            "GROUP BY c.nombre, ec.anioInscripcion " +
                            "ORDER BY c.nombre ASC, ec.anioInscripcion ASC",
                    Reporte.class
            ).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
