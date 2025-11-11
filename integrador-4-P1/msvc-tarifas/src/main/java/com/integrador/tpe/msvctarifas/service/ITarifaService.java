package com.integrador.tpe.msvctarifas.service;

import com.integrador.tpe.msvctarifas.dto.request.TarifaRequestDTO;
import com.integrador.tpe.msvctarifas.dto.response.EnviarTarifas;
import com.integrador.tpe.msvctarifas.dto.response.TarifaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITarifaService {
    EnviarTarifas getMontos();

    TarifaResponseDTO getTarifaById(Long id);

    Page<TarifaResponseDTO> getAllTarifas(Pageable pageable);

    TarifaResponseDTO createTarifa(TarifaRequestDTO tarifaRequestDTO);

    void deleteTarifa(Long id);

    TarifaResponseDTO updateTarifa(Long id, TarifaRequestDTO tarifaRequestDTO, Long idAdmin);
}
