package com.integrador.tpe.msvcfacturacion.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

public record CargaSaldoDTO(
        @NotNull @Positive
        BigDecimal monto
) implements Serializable {
}
