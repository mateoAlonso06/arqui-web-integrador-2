package com.integrador.tpe.msvcflota.repository;

import com.integrador.tpe.msvcflota.entity.Parada;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParadaRepository extends MongoRepository<Parada, ObjectId> {
    boolean existById(ObjectId idParada);
}
