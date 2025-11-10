package com.integrador.tpe.msvctarifas.dto.response;

import com.integrador.tpe.msvctarifas.entity.TipoTarifa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public record TarifaResponseDTO(
        Long id,
        TipoTarifa tipoTarifa,
        BigDecimal valorPorMinuto,
        LocalDateTime fechaVigencia
) implements Serializable {
}