package com.integrador.tpe.msvcfacturacion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saldos")
public class CuentaCorriente {
    @Id
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @Column(nullable = false, precision = 10, scale = 2, name = "saldo_actual", columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal saldoActual = new BigDecimal("0.00");

    @OneToMany(mappedBy = "cuentaCorriente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Transaccion> transacciones = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CuentaCorriente that = (CuentaCorriente) o;
        return Objects.equals(getIdCuenta(), that.getIdCuenta());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdCuenta());
    }
}
