package org.integrador;

import org.integrador.dto.CarreraDTO;
import org.integrador.factory.EntityManagerFactory;
import org.integrador.repository.impl.CarreraRepositoryImpl;
import org.integrador.repository.impl.EstudianteRepositoryImpl;
import org.integrador.repository.impl.ReporteRepositoryImpl;
import org.integrador.utils.Helper;

import javax.persistence.EntityManager;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        String path = "src/main/resources/";

        Helper Helper = new Helper();

        // Try para atrapar la checked exceptions que puedan ser lanzadas desde las distintas capas
        Helper.insertarEstudianteDesdeCSV("src/main/resources/estudiantes.csv");
        Helper.insertarCarreraDesdeCSV("src/main/resources/carreras.csv");
        Helper.insertarEstudianteCarreraDesdeCSV("src/main/resources/estudianteCarrera.csv");

        // Verificar que los datos se persistieron
//        verificarDatos();
        System.out.println("-----");

        EstudianteRepositoryImpl estudianteRepository = new EstudianteRepositoryImpl();

        try {
            // el metodo esta paginado, modificar estas variables para variar los resultados
            int page = 1;
            int cantAlumnos = 10;
            System.out.println("\n=== ESTUDIANTES (PAGINACIÓN) ===");
            estudianteRepository.getAllEstudiantes(page, cantAlumnos).forEach(System.out::println);
            System.out.println("-----");

            String libretaUniversitaria = "39711";
            System.out.println("\n=== BÚSQUEDA POR LIBRETA UNIVERSITARIA ===");
            System.out.println("Libreta universitaria: " + libretaUniversitaria);
            System.out.println(estudianteRepository.getEstudianteByLibretaUniversitaria(libretaUniversitaria));
            System.out.println("-----");

            System.out.println("\n=== ESTUDIANTES POR GÉNERO - MASCULINO ===");
            estudianteRepository.getEstudiantesByGenero("Male").forEach(System.out::println);
            System.out.println("-----");

            System.out.println("\n=== ESTUDIANTES POR GÉNERO - FEMENINO ===");
            estudianteRepository.getEstudiantesByGenero("Female").forEach(System.out::println);
            System.out.println("-----");

            CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();

            System.out.println("\n=== CARRERAS CON INSCRIPTOS ORDENADAS ===");
            carreraRepository.getAllCarrerasConInscriptos().forEach(System.out::println);
            System.out.println("-----");

            String nombreCarrera = "Educacion Fisica";
            String ciudad = "Jiaoyuan";
            CarreraDTO carreraDTO = carreraRepository.getCarreraByName(nombreCarrera);

            System.out.println("\n=== ESTUDIANTES POR CARRERA Y CIUDAD ===");
            System.out.println("Carrera: " + nombreCarrera + " | Ciudad: " + ciudad);
            carreraRepository.getEstudiantesByCarreraId(carreraDTO.getId(), ciudad).forEach(System.out::println);
            System.out.println("-----");

            ReporteRepositoryImpl reporteRepository = new ReporteRepositoryImpl();
            System.out.println("\n=== REPORTE DE ESTUDIANTES POR CARRERA ===");
            System.out.println("┌────────────────────────────────────────┬──────────────┬──────────────┬──────────────┐");
            System.out.println("│ CARRERA                                │ INSCRIPTOS   │ GRADUADOS    │ AÑO          │");
            System.out.println("├────────────────────────────────────────┼──────────────┼──────────────┼──────────────┤");

            String carreraAnterior = "";
            for (var reporte : reporteRepository.getReporte()) {
                if (!carreraAnterior.isEmpty() && !carreraAnterior.equals(reporte.getNombreCarrera())) {
                    System.out.println("├────────────────────────────────────────┼──────────────┼──────────────┼──────────────┤");
                }

                System.out.printf("│ %-38s │ %-12d │ %-12d │ %-12d │%n",
                        reporte.getNombreCarrera(),
                        reporte.getCantidadInscriptos(),
                        reporte.getCantidadGraduados(),
                        reporte.getAnio());

                carreraAnterior = reporte.getNombreCarrera();
            }

            System.out.println("└────────────────────────────────────────┴──────────────┴──────────────┴──────────────┘");
            System.out.println("-----");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void verificarDatos() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            Long countEstudiantes = em.createQuery("SELECT COUNT(e) FROM Estudiante e", Long.class).getSingleResult();
            Long countCarreras = em.createQuery("SELECT COUNT(c) FROM Carrera c", Long.class).getSingleResult();
            Long countEstudianteCarreras = em.createQuery("SELECT COUNT(ec) FROM EstudianteCarrera ec", Long.class).getSingleResult();

            System.out.println("=== RESUMEN DE DATOS PERSISTIDOS ===");
            System.out.println("Estudiantes: " + countEstudiantes);
            System.out.println("Carreras: " + countCarreras);
            System.out.println("EstudianteCarrera: " + countEstudianteCarreras);
        } finally {
            em.close();
        }
    }
}
