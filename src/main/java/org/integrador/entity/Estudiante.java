package org.integrador.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private List<EstudianteCarrera> estudianteCarreras;
}
