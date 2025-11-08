package com.integrador.tpe.msvcviajes.service;

public interface IPausaService {
    void pausarViaje(Long idViaje);

    void renaudarViaje(Long idPausa, Long idViaje);
}
