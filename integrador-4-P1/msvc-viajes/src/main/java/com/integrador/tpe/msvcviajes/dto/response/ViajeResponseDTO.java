package com.integrador.tpe.msvcviajes.dto.response;

public record ViajeResponseDTO(
    Long id,
    String idMonopatin,
    Long idCuenta,
    Double kmRecorridos
) {
}
