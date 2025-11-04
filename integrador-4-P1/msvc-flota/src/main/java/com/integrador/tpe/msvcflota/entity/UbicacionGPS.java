package com.integrador.tpe.msvcflota.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionGPS {
    private Double latitud;
    private Double longitud;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UbicacionGPS that = (UbicacionGPS) o;
        return Objects.equals(getLatitud(), that.getLatitud()) && Objects.equals(getLongitud(), that.getLongitud());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLatitud(), getLongitud());
    }
}
