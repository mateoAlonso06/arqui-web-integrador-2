package com.integrador.tpe.msvcfacturacion.service.impl;

import com.integrador.tpe.msvcfacturacion.dto.response.TransaccionResponseDTO;
import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import com.integrador.tpe.msvcfacturacion.exception.TransaccionNotFoundException;
import com.integrador.tpe.msvcfacturacion.mapper.TransaccionMapper;
import com.integrador.tpe.msvcfacturacion.repository.TransaccionRepository;
import com.integrador.tpe.msvcfacturacion.service.ITransaccionService;
import com.integrador.tpe.msvcfacturacion.specification.TransaccionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransaccionService implements ITransaccionService {
    private final TransaccionRepository transaccionRepository;
    private final TransaccionMapper transaccionMapper;

    @Override
    @Transactional(readOnly = true)
    public TransaccionResponseDTO getTransaccionById(Long idTransaccion) {
        Transaccion transaccion = transaccionRepository.findById(idTransaccion)
                .orElseThrow(() -> new TransaccionNotFoundException("Transaccion not found with id: " + idTransaccion));
        return transaccionMapper.toResponseDTO(transaccion);
    }

    @Override
    @Transactional
    public void eliminarTransaccionById(Long idTransaccion) {
        if (!transaccionRepository.existsById(idTransaccion))
            throw new TransaccionNotFoundException("Transaccion not found with id: " + idTransaccion);
        transaccionRepository.deleteById(idTransaccion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransaccionResponseDTO> getTransacciones(TransaccionFiltroDTO transaccionFiltroDTO) {
        Specification<Transaccion> spec = TransaccionSpecification.build(transaccionFiltroDTO);
        return transaccionRepository.findAll(spec)
                .stream()
                .map(transaccionMapper::toResponseDTO)
                .toList();
    }
}
