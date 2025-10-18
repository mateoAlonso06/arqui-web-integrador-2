package org.integrador.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarreraDTO {
    private Integer id;
    private String nombre;
    private Integer duracion;
    private Integer cantidadInscriptos;
}
