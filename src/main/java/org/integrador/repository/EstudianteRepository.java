package org.integrador.repository;

import org.integrador.dto.EstudianteDTO;
import org.integrador.entity.Estudiante;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EstudianteRepository implements IEstudianteRepository {
    public List<Estudiante> cargarDesdeCV() throws IOException {
        String filePath = "/home/mateo/Dev/integrador-2/src/main/resources/estudiantes.csv";
        List<Estudiante> estudiantes = new ArrayList<>();

        try (Stream<String> streamFIle = Files.lines(Paths.get(filePath))) {
            estudiantes = streamFIle.map(linea -> linea.split(",")).map(arreglo -> {
                Estudiante estudiante = new Estudiante();

                estudiante.setDni(arreglo[0]);
                estudiante.setNombre(arreglo[1]);
                estudiante.setApellido(arreglo[2]);
                estudiante.setEdad(Integer.parseInt(arreglo[3]));
                estudiante.setGenero(arreglo[4]);
                estudiante.setCiudad(arreglo[5]);
                estudiante.setNumeroLegajo(arreglo[6]);

                return estudiante;
            }).toList();
        } catch (Exception e) {
            throw e;
        }
        return estudiantes;
    }

    public EstudianteDTO addEstudiante(EstudianteDTO estudiante) {
        EntityManager em = Persistence.createEntityManagerFactory("integration").createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
