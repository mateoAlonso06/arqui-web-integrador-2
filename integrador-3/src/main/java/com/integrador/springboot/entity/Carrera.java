package com.integrador.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Carrera {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer duracion;

    @OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
    private List<EstudianteCarrera> estudianteCarrera = new ArrayList<>();
}