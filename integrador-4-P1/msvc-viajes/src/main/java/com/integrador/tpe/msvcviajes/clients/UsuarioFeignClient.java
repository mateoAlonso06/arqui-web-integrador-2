package com.integrador.tpe.msvcviajes.clients;

import com.integrador.tpe.msvcviajes.dto.interservice.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "msvc-usuarios", path = "/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/{id}/exists")
    boolean existUsuarioById(Long id);

    @GetMapping("/{idUsuario}/cuenta/{idCuenta}")
    boolean estaAsociadoConCuenta(Long idUsuario, Long idCuenta);
}
