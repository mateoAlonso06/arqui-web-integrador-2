package com.integrador.tpe.msvcfacturacion.dto.interservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TarifaDTO {
    private BigDecimal tarifaBase;
    private BigDecimal tarifaPausa;
}
