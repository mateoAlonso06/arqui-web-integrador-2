package com.integrador.tpe.msvcviajes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pausas")
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", updatable = false, nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    @PrePersist
    private void prePersist() {
        this.fechaInicio = LocalDateTime.now();
    }
}
