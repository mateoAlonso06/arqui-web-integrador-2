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
public class Carrera {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private Integer duracion;

    @OneToMany(mappedBy = "carrera")
    private List<EstudianteCarrera> estudianteCarreras;
}
