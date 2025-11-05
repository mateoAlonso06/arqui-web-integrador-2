package com.integrador.tpe.msvcflota.service;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IParadaService {
    Page<MonopatinResponseDTO> getMonopatinesEnParada(ObjectId idParada, Pageable pageable);

    ParadaResponseDTO addParada(ParadaRequestDTO paradaRequestDTO);

    void deleteParada(ObjectId id);

    ParadaResponseDTO getParadaById(ObjectId id);
}
