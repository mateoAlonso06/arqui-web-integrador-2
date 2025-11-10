package com.integrador.tpe.msvcflota.service.impl;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.EstadoMonopatin;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import com.integrador.tpe.msvcflota.exception.MonopatinNotFoundException;
import com.integrador.tpe.msvcflota.mapper.MonopatinMapper;
import com.integrador.tpe.msvcflota.repository.MonopatinRepository;
import com.integrador.tpe.msvcflota.service.IMonopatinService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonopatinService implements IMonopatinService {
    private final MonopatinRepository monopatinRepository;
    private final MonopatinMapper monopatinMapper;

    @Override
    @Transactional
    public void deleteMonopatin(String id) {
        ObjectId objectId = new ObjectId(id);

        Monopatin monopatin = monopatinRepository.findById(objectId)
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + id));

        if (monopatin.getEstado().equals(EstadoMonopatin.EN_USO))
            throw new IllegalArgumentException("No se puede eliminar un monopatín que está en uso.");

        monopatinRepository.deleteById(objectId);
    }

    @Override
    @Transactional
    public MonopatinResponseDTO addMonopatin(MonopatinRequestDTO monopatinRequestDTO) {
        UbicacionGPS ubicacion = monopatinRequestDTO.ubicacionGps();

        // Mejora: hacerlo a nivel DTO -> sigue principio de "fail fast"
        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null)
            throw new IllegalArgumentException("La ubicación GPS no puede tener valores nulos para latitud o longitud.");

        Monopatin toSave = monopatinMapper.toEntity(monopatinRequestDTO);
        Monopatin saved = monopatinRepository.save(toSave);

        return monopatinMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MonopatinResponseDTO getMonopatinById(String id) {
        ObjectId objectId = new ObjectId(id);

        Monopatin monopatin = monopatinRepository.findById(objectId)
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + id));

        return monopatinMapper.toResponseDTO(monopatin);
    }

    @Override
    public void actualizarEstadoMonopatin(String idMonopatin, String estado) {
        Monopatin monopatin = monopatinRepository.findById(new ObjectId(idMonopatin))
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + idMonopatin));

        monopatin.setEstado(EstadoMonopatin.valueOf(estado));
        monopatinRepository.save(monopatin);
    }

    @Override
    public void actualizarRecorridoMonopatin(String idMonopatin, Double kmRecorridos) {
        Monopatin monopatin = monopatinRepository.findById(new ObjectId(idMonopatin))
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + idMonopatin));

        if (kmRecorridos == null || kmRecorridos < 0)
            throw new IllegalArgumentException("Los kilómetros recorridos no pueden ser nulos o negativos.");

        monopatin.setKmRecorridos(monopatin.getKmRecorridos() + kmRecorridos);
        monopatinRepository.save(monopatin);
    }

    @Override
    public void actualizarUbicacionMonopatin(String idMonopatin, UbicacionGPS nuevaUbicacion) {
        ObjectId objectId = new ObjectId(idMonopatin);
        Monopatin monopatin = monopatinRepository.findById(objectId)
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + idMonopatin));

        monopatin.setUbicacionGps(nuevaUbicacion);
        monopatinRepository.save(monopatin);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MonopatinResponseDTO> getAllMonopatines(Pageable pageable) {
        Page<Monopatin> monopatinesPage = monopatinRepository.findAll(pageable);
        return monopatinesPage.map(monopatinMapper::toResponseDTO);
    }
}
