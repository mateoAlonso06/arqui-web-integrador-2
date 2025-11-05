package com.integrador.tpe.msvcviajes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_monopatin")
    private Long idMonopatin; // fk a Monopatin

    @Column(name = "id_usuario")
    private Long idUsuario; // fk a Usuario

    @Column(name = "id_cuenta")
    private Long idCuenta; // fk a Cuenta

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "km_recorridos")
    private Double kmRecorridos;

    @OneToMany(mappedBy = "viaje")
    private List<Pausa> pausas;

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
