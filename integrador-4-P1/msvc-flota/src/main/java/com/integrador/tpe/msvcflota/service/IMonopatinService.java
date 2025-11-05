package com.integrador.tpe.msvcflota.service;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMonopatinService {
    void deleteMonopatin(ObjectId id);

    MonopatinResponseDTO addMonopatin(MonopatinRequestDTO monopatinRequestDTO);

    Page<MonopatinResponseDTO> getAllMonopatines(Pageable pageable);

    MonopatinResponseDTO getMonopatinById(ObjectId id);
}
