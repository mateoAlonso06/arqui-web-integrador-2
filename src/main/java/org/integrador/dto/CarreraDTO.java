package org.integrador.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CarreraDTO {
    private final Integer id;
    private final String nombre;
    private final Integer duracion;
}
