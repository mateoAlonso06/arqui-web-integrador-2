package org.integrador;

import org.integrador.dto.CarreraDTO;
import org.integrador.factory.JPAUtil;
import org.integrador.repository.impl.CarreraRepositoryImpl;
import org.integrador.repository.impl.EstudianteRepositoryImpl;
import org.integrador.repository.impl.ReporteRepositoryImpl;

import javax.persistence.EntityManager;
import java.io.IOException;


public class App {
    public static void main(String[] args) throws IOException {
        String path = "src/main/resources/";

        JPAUtil jpaUtil = new JPAUtil();

        // Try para atrapar la checked exceptions que puedan ser lanzadas desde las distintas capas
        jpaUtil.insertarEstudianteDesdeCSV("src/main/resources/estudiantes.csv");
        jpaUtil.insertarCarreraDesdeCSV("src/main/resources/carreras.csv");
        jpaUtil.insertarEstudianteCarreraDesdeCSV("src/main/resources/estudianteCarrera.csv");

        // Verificar que los datos se persistieron
        verificarDatos();
        System.out.println("-----");

        EstudianteRepositoryImpl estudianteRepository = new EstudianteRepositoryImpl();
        // el metodo esta paginado, modificar estas variables para variar los resultados

        try {
            int page = 1;
            int cantAlumnos = 10;
            estudianteRepository.getAllEstudiantes(page, cantAlumnos).forEach(System.out::println);
            System.out.println("-----");

            String libretaUniversitaria = "39711"; // 45421127,Fredi,Jovicevic,48,Female,Fushë-Muhurr,39711
            System.out.println("Recuperar estudiante con libreta universitaria: " + libretaUniversitaria);
            System.out.println(estudianteRepository.getEstudianteByLibretaUniversitaria(libretaUniversitaria));
            System.out.println("-----");

            System.out.println("Estudiantes masculinos:");
            estudianteRepository.getEstudiantesByGenero("Male").forEach(System.out::println);
            System.out.println("-----");

            System.out.println("Estudiantes femeninos:");
            estudianteRepository.getEstudiantesByGenero("Female").forEach(System.out::println);
            System.out.println("-----");

            CarreraRepositoryImpl carreraRepository = new CarreraRepositoryImpl();

            System.out.println("Carreras con inscriptos ordenada por cantidad:");
            carreraRepository.getAllCarrerasConInscriptos().forEach(System.out::println);
            System.out.println("-----");

            String nombreCarrera = "Educacion Fisica";
            String ciudad = "Jiaoyuan";
            CarreraDTO carreraDTO = carreraRepository.getCarreraByName(nombreCarrera);

            System.out.println("Estudiantes de la carrera: " + nombreCarrera);
            carreraRepository.getEstudiantesByCarreraId(carreraDTO.getId(), ciudad).forEach(System.out::println);
            System.out.println("-----");

            ReporteRepositoryImpl reporteRepository = new ReporteRepositoryImpl();
            System.out.println("\nReporte de estudiantes por carrera:");
            reporteRepository.getReporte().forEach(System.out::println);
            System.out.println("-----");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void verificarDatos() {
        EntityManager em = JPAUtil.getEntityManager();
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
