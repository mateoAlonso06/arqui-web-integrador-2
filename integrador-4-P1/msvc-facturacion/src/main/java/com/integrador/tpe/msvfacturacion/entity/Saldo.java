package com.integrador.tpe.msvfacturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saldos")
public class Saldo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2, name = "saldo_actual", columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal saldoActual = new BigDecimal(0.00);
}
