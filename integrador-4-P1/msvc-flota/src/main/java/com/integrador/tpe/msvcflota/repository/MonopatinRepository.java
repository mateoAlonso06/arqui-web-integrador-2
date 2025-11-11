package com.integrador.tpe.msvcflota.repository;

import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonopatinRepository extends MongoRepository<Monopatin, ObjectId> {
    Page<Monopatin> findAllByUbicacionGpsAndEstado(UbicacionGPS ubicacionGps, EstadoMonopatin estadoMonopatin, Pageable pageable);
}
