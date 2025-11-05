package com.integrador.tpe.msvcflota.service.impl;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.entity.Parada;
import com.integrador.tpe.msvcflota.exception.ParadaNotFoundException;
import com.integrador.tpe.msvcflota.mapper.MonopatinMapper;
import com.integrador.tpe.msvcflota.mapper.ParadaMapper;
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
    private final ParadaMapper paradaMapper;

    @Override
    public Page<MonopatinResponseDTO> getMonopatinesEnParada(ObjectId idParada, Pageable pageable) {
        if (!paradaRepository.existById(idParada))
            throw new ParadaNotFoundException("No existe parada con ID: " + idParada);

        Page<Monopatin> monopatines = monopatinRepository.findAllByParadaInfo_IdParada(idParada, pageable);

        return monopatines.map(monopatinMapper::toResponseDTO);
    }

    @Override
    public ParadaResponseDTO addParada(ParadaRequestDTO paradaRequestDTO) {
        Parada toSave = paradaMapper.toEntity(paradaRequestDTO);
        Parada saved = paradaRepository.save(toSave);
        return paradaMapper.toResponseDTO(saved);
    }

    @Override
    public void deleteParada(ObjectId id) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new ParadaNotFoundException("No existe parada con ID: " + id));

        // No puede haber monopatines en esta parada
        if (this.getMonopatinesEnParada(parada.getId(), Pageable.unpaged()).hasContent()) {
            throw new IllegalStateException("No se puede eliminar la parada porque hay monopatines asignados a ella.");
        }
        paradaRepository.delete(parada);
    }

    @Override
    public ParadaResponseDTO getParadaById(ObjectId id) {
        Parada parada = paradaRepository.findById(id)
                .orElseThrow(() -> new ParadaNotFoundException("No existe parada con ID: " + id));
        return paradaMapper.toResponseDTO(parada);
    }
}
