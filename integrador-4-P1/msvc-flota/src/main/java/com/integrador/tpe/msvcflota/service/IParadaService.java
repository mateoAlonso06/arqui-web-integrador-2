package com.integrador.tpe.msvcflota.service;

import com.integrador.tpe.msvcflota.dto.MonopatinResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IParadaService {
    Page<MonopatinResponseDTO> getMonopatinesEnParada(ObjectId idParada, Pageable pageable);
}
