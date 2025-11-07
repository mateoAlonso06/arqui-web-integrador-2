package com.integrador.tpe.msvcviajes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViajeRequestDTO {
    @NotNull
    @Positive
    private Long idMonopatin;

    @NotNull
    @Positive
    private Long idCuenta;

    @NotNull
    @Positive
    private Long idUsuario;

    @Positive
    private Double kmRecorridos;
}
