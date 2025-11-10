package com.integrador.tpe.msvcflota.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "monopatines")
public class Monopatin {
    @Id
    private ObjectId id;
    private UbicacionGPS ubicacionGps;
    private Double kmRecorridos = 0.0;
    private EstadoMonopatin estado = EstadoMonopatin.LIBRE;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Monopatin monopatin = (Monopatin) o;
        return Objects.equals(getId(), monopatin.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
