package com.integrador.tpe.msvctarifas.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tarifas")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_de_tarifa", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTarifa tipoTarifa;

    @Column(name = "valor_por_minuto", nullable = false)
    private Double valorPorMinuto;

    @Column(name = "fecha_vigencia", nullable = false)
    private LocalDateTime fechaVigencia;
}
