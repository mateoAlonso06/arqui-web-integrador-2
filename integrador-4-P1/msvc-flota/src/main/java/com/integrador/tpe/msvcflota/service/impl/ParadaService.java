package com.integrador.tpe.msvcflota.service.impl;

import com.integrador.tpe.msvcflota.dto.request.ParadaRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.dto.responses.ParadaResponseDTO;
import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.entity.Parada;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParadaService implements IParadaService {
    private final ParadaRepository paradaRepository;
    private final MonopatinRepository monopatinRepository;
    private final MonopatinMapper monopatinMapper;
    private final ParadaMapper paradaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<MonopatinResponseDTO> getMonopatinesEnParada(String idParada, Pageable pageable) {
        ObjectId objectId = new ObjectId(idParada);
        Parada parada = paradaRepository.findById(objectId)
                .orElseThrow(() -> new ParadaNotFoundException("No existe parada con ID: " + idParada));

        Page<Monopatin> monopatines = monopatinRepository.findAllByUbicacionGpsAndEstado(parada.getUbicacionGps(), EstadoMonopatin.LIBRE, pageable);

        return monopatines.map(monopatinMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public ParadaResponseDTO addParada(ParadaRequestDTO paradaRequestDTO) {
        UbicacionGPS ubicacion = paradaRequestDTO.ubicacionGps();

        Double latitud = ubicacion.getLatitud();
        Double longitud = ubicacion.getLongitud();

        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null)
            throw new IllegalArgumentException("La ubicación GPS debe tener latitud y longitud no nulas");

        if (paradaRepository.existsByUbicacionGps_LatitudAndUbicacionGps_Longitud(latitud, longitud))
            throw new IllegalArgumentException("La parada en esta ubicación ya existe.");

        Parada toSave = paradaMapper.toEntity(paradaRequestDTO);
        Parada saved = paradaRepository.save(toSave);

        return paradaMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParadaResponseDTO> getAllParadas(Pageable pageable) {
        Page<Parada> paradas = paradaRepository.findAll(pageable);
        return paradas.map(paradaMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public void deleteParada(String id) {
        ObjectId objectId = new ObjectId(id);
        Parada parada = paradaRepository.findById(objectId)
                .orElseThrow(() -> new ParadaNotFoundException("No existe parada con ID: " + id));

        // No puede haber monopatines en esta parada
        if (this.getMonopatinesEnParada(id, Pageable.unpaged()).hasContent())
            throw new IllegalStateException("No se puede eliminar la parada porque hay monopatines asignados a ella.");

        paradaRepository.delete(parada);
    }

    @Override
    @Transactional(readOnly = true)
    public ParadaResponseDTO getParadaById(String id) {
        ObjectId objectId = new ObjectId(id);
        Parada parada = paradaRepository.findById(objectId)
                .orElseThrow(() -> new ParadaNotFoundException("No existe parada con ID: " + id));

        return paradaMapper.toResponseDTO(parada);
    }
}
