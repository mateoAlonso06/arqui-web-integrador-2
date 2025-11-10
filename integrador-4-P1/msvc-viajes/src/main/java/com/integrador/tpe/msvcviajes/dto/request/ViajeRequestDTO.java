package com.integrador.tpe.msvcviajes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ViajeRequestDTO implements Serializable {
    @NotNull
    private String idMonopatin;
    @NotNull
    @Positive
    private Long idCuenta;
    @NotNull
    private String idParada;
    @NotNull
    @Positive
    private Long idUsuario;
    @Positive
    private Double kmRecorridos;
}
