package com.integrador.tpe.msvcviajes.dto.interservice.response;

import java.io.Serializable;

public record UsuarioResponseDTO(
    Long id,
    Long cantidadViajes
) implements Serializable {
}
