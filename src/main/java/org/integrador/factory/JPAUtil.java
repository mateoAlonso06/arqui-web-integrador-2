package org.integrador.factory;

import com.opencsv.CSVReader;
import org.integrador.entity.Carrera;
import org.integrador.entity.Estudiante;
import org.integrador.entity.EstudianteCarrera;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;

public class JPAUtil {
    private static final EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("arqui-web-i2");
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void insertarEstudianteCarreraDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                try {
                    EstudianteCarrera ec = new EstudianteCarrera();

                    System.out.println("Procesando línea: " + String.join(",", linea));
                    System.out.println("Buscando estudiante con DNI: " + linea[1]);
                    System.out.println("Buscando carrera con ID: " + linea[2]);

                    Estudiante est = em.find(Estudiante.class, linea[1]);
                    if (est == null) {
                        System.out.println("ERROR: No se encontró estudiante con DNI: " + linea[1]);
                    }

                    Carrera carrera = em.find(Carrera.class, Integer.parseInt(linea[2]));
                    if (carrera == null) {
                        System.out.println("ERROR: No se encontró carrera con ID: " + linea[2]);
                    }

                    ec.setEstudiante(est);
                    ec.setCarrera(carrera);
                    ec.setAnioInscripcion(Integer.parseInt(linea[3]));
                    ec.setAntiguedad(Integer.parseInt(linea[5])); // Columna 5 es antiguedad

                    em.persist(ec);
                    System.out.println("EstudianteCarrera persistido correctamente");

                } catch (Exception lineException) {
                    System.out.println("Error procesando línea: " + String.join(",", linea));
                    lineException.printStackTrace();
                }
            }
            em.getTransaction().commit();
            System.out.println("Transacción completada exitosamente");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void insertarEstudianteDesdeCSV(String rutaArchivo) {
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

    public void insertarCarreraDesdeCSV(String rutaArchivo) {
        EntityManager em = JPAUtil.getEntityManager();

        try (CSVReader reader = new CSVReader(new FileReader(rutaArchivo))) {
            String[] linea;
            reader.readNext();

            em.getTransaction().begin();

            while ((linea = reader.readNext()) != null) {
                Carrera carrera = new Carrera();
                carrera.setId(Integer.parseInt(linea[0]));
                carrera.setNombre(linea[1]);
                carrera.setDuracion(Integer.parseInt(linea[2]));
                em.persist(carrera);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
