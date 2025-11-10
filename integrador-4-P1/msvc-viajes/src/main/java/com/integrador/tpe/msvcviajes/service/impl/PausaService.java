package com.integrador.tpe.msvcviajes.service.impl;

import com.integrador.tpe.msvcviajes.entity.Pausa;
import com.integrador.tpe.msvcviajes.entity.Viaje;
import com.integrador.tpe.msvcviajes.exception.PausaNotFoundException;
import com.integrador.tpe.msvcviajes.exception.ViajeNotFoundException;
import com.integrador.tpe.msvcviajes.repository.PausaRepository;
import com.integrador.tpe.msvcviajes.repository.ViajeRepository;
import com.integrador.tpe.msvcviajes.service.IPausaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PausaService implements IPausaService {
    private final PausaRepository pausaRepository;
    private final ViajeRepository viajeRepository;

    @Override
    @Transactional
    public void pausarViaje(Long idViaje) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new ViajeNotFoundException("Viaje no encontrado con id: " + idViaje));

        // No causa demasiado costo extra en comparacion de comprobarlo en la DB
        boolean existePausaActiva = viaje.getPausas()
                                    .stream()
                                    .anyMatch(p -> p.getFechaFin() == null);

        Pausa pausa = Pausa.builder()
                    .viaje(viaje)
                    .build();

        viaje.getPausas().add(pausa);
        pausaRepository.save(pausa);
    }

    @Override
    @Transactional
    public void renaudarViaje(Long idPausa, Long idViaje) {
        Pausa pausa = pausaRepository.findByIdAndViaje_Id(idPausa, idViaje)
                .orElseThrow(() -> new PausaNotFoundException("Pausa no encontrada con id: " + idPausa));

        pausa.setFechaFin(LocalDateTime.now());
        pausaRepository.save(pausa);
    }
}
