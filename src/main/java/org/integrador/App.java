package org.integrador;

import org.integrador.repository.EstudianteRepository;

import java.io.IOException;


public class App {
    public static void main( String[] args ) throws IOException {
        EstudianteRepository repo = new EstudianteRepository();
        repo.insertarDesdeCSV("src/main/resources/estudiantes.csv");
    }
}
