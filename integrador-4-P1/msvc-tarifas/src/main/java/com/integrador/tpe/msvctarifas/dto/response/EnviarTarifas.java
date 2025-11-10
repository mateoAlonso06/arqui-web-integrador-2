package com.integrador.tpe.msvctarifas.dto.response;

import java.math.BigDecimal;

public record EnviarTarifas(
        BigDecimal tarifaBase,
        BigDecimal tarifaPausa
) {
}
