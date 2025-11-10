package com.integrador.tpe.msvctarifas.dto.request;

import com.integrador.tpe.msvctarifas.entity.TipoTarifa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TarifaRequestDTO(
        @NotNull
        TipoTarifa tipoTarifa,
        @NotNull @Positive
        BigDecimal valorPorMinuto,
        @NotNull
        LocalDateTime fechaVigencia
) implements Serializable {
}
