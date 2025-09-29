package org.integrador.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EstudianteCarrera {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(length = 4, nullable = false)
    private Integer anioInscripcion;

    @Column(length = 4, nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer graduacion = 0;

    @Column(length = 4, nullable = false)
    private Integer antiguedad;
//    public Integer getAntiguedad() {
//        if (this.graduacion != null && this.graduacion != 0) {
//            return this.graduacion - this.anioInscripcion;
//        } else {
//            int anioActual = java.time.Year.now().getValue();
//            return anioActual - this.anioInscripcion;
//        }
//    }
}
