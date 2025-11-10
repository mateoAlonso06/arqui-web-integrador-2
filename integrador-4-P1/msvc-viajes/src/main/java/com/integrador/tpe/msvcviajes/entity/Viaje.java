package com.integrador.tpe.msvcviajes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "viajes")
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_monopatin", nullable = false)
    private String idMonopatin; // fk a Monopatin

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario; // fk a Usuario

    @Column(name = "id_cuenta", nullable = false)
    private Long idCuenta; // fk a Cuenta

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "km_recorridos")
    private Double kmRecorridos;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pausa> pausas = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Viaje viaje = (Viaje) o;
        return Objects.equals(getId(), viaje.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
