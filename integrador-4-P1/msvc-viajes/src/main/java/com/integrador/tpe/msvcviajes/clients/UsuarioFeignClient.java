package com.integrador.tpe.msvcviajes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuarios", contextId = "usuariosClient", path = "/usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/{id}/exists")
    boolean existUsuarioById(@PathVariable Long id);

    @GetMapping("/{idUsuario}/cuenta/{idCuenta}")
    boolean estaAsociadoConCuenta(@PathVariable Long idUsuario, @PathVariable Long idCuenta);
}
