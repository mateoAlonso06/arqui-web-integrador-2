package com.integrador.tpe.msvcmantenimiento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoMantenimiento {
    @Id
    @Column(name = "id_monopatin")
    private Long idMonopatin;

    @Column(name = "en_mantenimiento", nullable = false)
    private boolean enMantenimiento;

    @Column(name = "km_acumulados")
    private Double kmAcumulados;
}
