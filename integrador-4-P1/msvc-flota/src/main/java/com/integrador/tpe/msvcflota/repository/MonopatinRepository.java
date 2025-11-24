package com.integrador.tpe.msvcflota.repository;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends MongoRepository<Monopatin, ObjectId> {
    List<Monopatin> findAllByUbicacionGpsAndEstado(UbicacionGPS ubicacionGps, EstadoMonopatin estadoMonopatin);
}
