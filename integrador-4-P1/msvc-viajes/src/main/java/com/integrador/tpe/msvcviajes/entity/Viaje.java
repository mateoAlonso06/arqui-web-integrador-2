package com.integrador.tpe.msvcviajes.entity;

import com.integrador.tpe.msvcviajes.dto.interservice.request.TipoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false)
    private TipoCuenta tipoCuenta = TipoCuenta.BASICA;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "km_recorridos")
    private Double kmRecorridos = 0.0;

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pausa> pausas = new ArrayList<>();

    @Column(name = "tiempo_pausa")
    private Double tiempoPausa = 0.0;

    @PreUpdate
    private void calcularTiempos() {
        // tiempo de pausa
        if (pausas.isEmpty())
            return;
        double totalPausa = 0.0;
        for (Pausa pausa : pausas) {
            if (pausa.getFechaFin() != null && pausa.getFechaInicio() != null) {
                totalPausa += Duration.between(pausa.getFechaInicio(), pausa.getFechaFin()).toMinutes();
                tiempoPausa = totalPausa;
            }
        }
    }

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

    public double calcularTiempoViaje() {
        if (fechaInicio != null && fechaFin != null) {
            return (double) Duration.between(fechaInicio, fechaFin).toMinutes();
        }
        return 0.0;
    }
}
