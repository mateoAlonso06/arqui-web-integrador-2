package com.integrador.tpe.msvcflota.service.impl;

import com.integrador.tpe.msvcflota.dto.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.Monopatin;
import com.integrador.tpe.msvcflota.exception.MonopatinNotFoundException;
import com.integrador.tpe.msvcflota.mapper.MonopatinMapper;
import com.integrador.tpe.msvcflota.repository.MonopatinRepository;
import com.integrador.tpe.msvcflota.service.IMonopatinService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonopatinService implements IMonopatinService {
    private final MonopatinRepository monopatinRepository;
    private final MonopatinMapper monopatinMapper;

    @Override
    public void deleteMonopatin(ObjectId id) {
        if (monopatinRepository.existsById(id))
            throw new MonopatinNotFoundException("No existe monopatín con ID: " + id);

        monopatinRepository.deleteById(id);
    }

    @Override
    public MonopatinResponseDTO addMonopatin(MonopatinRequestDTO monopatinRequestDTO) {
        Monopatin toSave = monopatinMapper.toEntity(monopatinRequestDTO);
        Monopatin saved = monopatinRepository.save(toSave);
        return monopatinMapper.toResponseDTO(saved);
    }

    @Override
    public MonopatinResponseDTO getMonopatinById(ObjectId id) {
        Monopatin monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new MonopatinNotFoundException("No existe monopatín con ID: " + id));
        return monopatinMapper.toResponseDTO(monopatin);
    }

    @Override
    public Page<MonopatinResponseDTO> getAllMonopatines(Pageable pageable) {
        Page<Monopatin> monopatinesPage = monopatinRepository.findAll(pageable);
        return monopatinesPage.map(monopatinMapper::toResponseDTO);
    }
}
