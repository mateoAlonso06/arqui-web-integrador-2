package org.integrador.repository;


import com.opencsv.CSVReader;
import org.integrador.entity.Estudiante;
import org.integrador.factory.JPAUtil;

import javax.persistence.EntityManager;
import java.io.FileReader;


public class EstudianteRepository implements IEstudianteRepository {
    @Override
    public void insertarDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Estudiante est = new Estudiante();
                est.setDni(linea[0]);
                est.setNombre(linea[1]);
                est.setApellido(linea[2]);
                est.setEdad(Integer.parseInt(linea[3]));
                est.setGenero(linea[4]);
                est.setCiudad(linea[5]);
                est.setNumeroLegajo(linea[6]);
                em.persist(est);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
