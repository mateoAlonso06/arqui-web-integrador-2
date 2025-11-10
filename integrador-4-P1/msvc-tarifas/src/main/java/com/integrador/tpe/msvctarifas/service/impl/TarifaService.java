package com.integrador.tpe.msvctarifas.service.impl;

import com.integrador.tpe.msvctarifas.dto.request.TarifaRequestDTO;
import com.integrador.tpe.msvctarifas.dto.response.EnviarTarifas;
import com.integrador.tpe.msvctarifas.dto.response.TarifaResponseDTO;
import com.integrador.tpe.msvctarifas.entity.Tarifa;
import com.integrador.tpe.msvctarifas.entity.TipoTarifa;
import com.integrador.tpe.msvctarifas.mapper.TarifaMapper;
import com.integrador.tpe.msvctarifas.repository.TarifaRepository;
import com.integrador.tpe.msvctarifas.service.ITarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarifaService implements ITarifaService {
    private final TarifaRepository tarifaRepository;
    private final TarifaMapper tarifaMapper;

    @Override
    @Transactional(readOnly = true)
    public EnviarTarifas getMontos() {
        List<Tarifa> tarifas = tarifaRepository.findAll();

        BigDecimal tarifaBase = java.math.BigDecimal.ZERO;
        BigDecimal tarifaPausa = java.math.BigDecimal.ZERO;

        for (Tarifa tarifa : tarifas) {
            if (tarifa.getTipoTarifa() == TipoTarifa.BASICA) {
                tarifaBase = tarifa.getValorPorMinuto();
            } else {
                tarifaPausa = tarifa.getValorPorMinuto();
            }
        }
        return new EnviarTarifas(tarifaBase, tarifaPausa);
    }

    @Override
    @Transactional(readOnly = true)
    public TarifaResponseDTO getTarifaById(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa not found with id: " + id));
        return tarifaMapper.toResponseDTO(tarifa);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TarifaResponseDTO> getAllTarifas(Pageable pageable) {
        Page<Tarifa> tarifas = tarifaRepository.findAll(pageable);
        return tarifas.map(tarifaMapper::toResponseDTO);
    }

    @Override
    @Transactional
    public TarifaResponseDTO createTarifa(TarifaRequestDTO tarifaRequestDTO) {
        Tarifa tarifa = tarifaMapper.toEntity(tarifaRequestDTO);
        Tarifa savedTarifa = tarifaRepository.save(tarifa);
        return tarifaMapper.toResponseDTO(savedTarifa);
    }

    @Override
    @Transactional
    public void deleteTarifa(Long id) {
        if (!tarifaRepository.existsById(id))
            throw new RuntimeException("Tarifa not found with id: " + id);
        tarifaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TarifaResponseDTO updateTarifa(Long id, TarifaRequestDTO tarifaRequestDTO) {
        Tarifa existingTarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa not found with id: " + id));

        if (tarifaRequestDTO.tipoTarifa() != null)
            existingTarifa.setTipoTarifa(tarifaRequestDTO.tipoTarifa());

        if (tarifaRequestDTO.valorPorMinuto() != null)
            existingTarifa.setValorPorMinuto(tarifaRequestDTO.valorPorMinuto());

        if (tarifaRequestDTO.fechaVigencia() != null)
            existingTarifa.setFechaVigencia(tarifaRequestDTO.fechaVigencia());

        Tarifa updatedTarifa = tarifaRepository.save(existingTarifa);
        return tarifaMapper.toResponseDTO(updatedTarifa);
    }
}
