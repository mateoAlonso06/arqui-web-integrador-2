package com.integrador.tpe.msvcflota.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
public class ParadaInfo {
    private ObjectId idParada;
    private String nombreParada;
}
