package com.integrador.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Estudiante {
    @Id
    private String dni;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private String ciudad;

    @Column(name = "libreta_universitaria", nullable = false)
    private String libretaUniversitaria;

    @OneToMany(mappedBy = "estudiante", fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudianteCarreras = new ArrayList<>();
}