package org.integrador;

import org.integrador.factory.JPAUtil;

import javax.persistence.EntityManager;
import java.io.IOException;


public class App {
    public static void main( String[] args ) throws IOException {
        String path = "src/main/resources/";

        JPAUtil jpaUtil = new JPAUtil();

        jpaUtil.insertarEstudianteDesdeCSV("src/main/resources/estudiantes.csv");
        jpaUtil.insertarCarreraDesdeCSV("src/main/resources/carreras.csv");
        jpaUtil.insertarEstudianteCarreraDesdeCSV("src/main/resources/estudianteCarrera.csv");

        // Verificar que los datos se persistieron
        verificarDatos();
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
