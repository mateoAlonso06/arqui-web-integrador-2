package com.integrador.tpe.msvcflota.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document(collection = "paradas")
public class Parada {
    @Id
    private ObjectId id;
    private UbicacionGPS ubicacionGps;
    private String nombre;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parada parada = (Parada) o;
        return Objects.equals(id, parada.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
