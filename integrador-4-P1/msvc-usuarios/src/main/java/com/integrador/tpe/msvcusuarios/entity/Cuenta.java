package com.integrador.tpe.msvcusuarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuentas")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Solo se puede conectar una unica cuenta de mercado pago a una cuenta de usuario
    @Column(nullable = false, name = "id_cuenta_mercado_pago", unique = true)
    private Long idCuentaMercadoPago;

    @Column(nullable = false, name = "fecha_alta", updatable = false)
    private LocalDateTime fechaAlta;

    // El admin tendra la posibilidad de deshabilitar la cuenta de un usuario
    @Column(name = "estado_cuenta", nullable = false, columnDefinition = "boolean default true")
    private Boolean estadoCuenta = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta", nullable = false, columnDefinition = "varchar(20) default 'BASICA'")
    private TipoCuenta tipoCuenta;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.MERGE
    })
    @JoinTable(
            name = "cuentas_usuarios",
            joinColumns = @JoinColumn(name = "cuenta_id"), // fk
            inverseJoinColumns = @JoinColumn(name = "usuario_id") // fk
    )
    private Set<Usuario> usuarios = new HashSet<>();

    @PrePersist
    private void prePersist() {
        this.fechaAlta = LocalDateTime.now();
    }

    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.getCuentas().remove(this);
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getCuentas().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(getId(), cuenta.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
