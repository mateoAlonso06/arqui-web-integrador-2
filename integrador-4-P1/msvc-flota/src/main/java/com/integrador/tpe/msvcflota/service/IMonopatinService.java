package com.integrador.tpe.msvcflota.service;

import com.integrador.tpe.msvcflota.dto.request.MonopatinRequestDTO;
import com.integrador.tpe.msvcflota.dto.responses.MonopatinResponseDTO;
import com.integrador.tpe.msvcflota.entity.UbicacionGPS;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMonopatinService {
    void deleteMonopatin(String id);

    MonopatinResponseDTO addMonopatin(MonopatinRequestDTO monopatinRequestDTO);

    Page<MonopatinResponseDTO> getAllMonopatines(Pageable pageable);

    MonopatinResponseDTO getMonopatinById(String id);

    void actualizarEstadoMonopatin(String idMonopatin, String estado);

    void actualizarRecorridoMonopatin(String idMonopatin, Double kmRecorridos);

    void actualizarUbicacionMonopatin(String idMonopatin, UbicacionGPS nuevaUbicacion);

    boolean verificarExistenciaMonopatin(String idMonopatin);

    void deshabilitarMonopatin(String idMonopatin);

    void habilitarMonopatin(@NotBlank String idMonopatin);
}
