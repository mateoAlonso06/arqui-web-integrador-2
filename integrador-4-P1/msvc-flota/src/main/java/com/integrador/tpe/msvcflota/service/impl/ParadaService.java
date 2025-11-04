package com.integrador.tpe.msvcflota.service.impl;

import com.integrador.tpe.msvcflota.dto.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.exception.ParadaNotFoundException;
import com.integrador.tpe.msvcflota.mapper.MonopatinMapper;
import com.integrador.tpe.msvcflota.repository.MonopatinRepository;
import com.integrador.tpe.msvcflota.repository.ParadaRepository;
import com.integrador.tpe.msvcflota.service.IParadaService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParadaService implements IParadaService {
    private final ParadaRepository paradaRepository;
    private final MonopatinRepository monopatinRepository;
    private final MonopatinMapper monopatinMapper;

    @Override
    public Page<MonopatinResponseDTO> getMonopatinesEnParada(ObjectId idParada, Pageable pageable) {
        if (!paradaRepository.existById(idParada))
            throw new ParadaNotFoundException("No existe parada con ID: " + idParada);

        Page<Monopatin> monopatines = monopatinRepository.findAllByParadaInfo_IdParada(idParada, pageable);

        return monopatines.map(monopatinMapper::toResponseDTO);
    }
}
